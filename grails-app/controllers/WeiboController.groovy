import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import grails.converters.JSON
import groovy.time.TimeCategory
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils

/**
 * weibo授权
 *
 * @see http://open.weibo.com/wiki/Oauth2/authorize
 * url:http://www.onlysleep.net/weibo/accessToken
 */
class WeiboController {
    def properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource('app.properties'))
    def appId = properties.getProperty("appKey")
    def appSecret = properties.getProperty("appSecret")
    def weiboHttpsService
    def cookieService
    def dataSource
    def MAX_DEEP = 9//最多递归9次
    def baseUrl = "http://m.weibo.cn/n/"


    def index() {
        if (!params.passed) {
            redirect(action: "oauth")
        } else {
            redirect(action: "features")
        }
    }

    def home = {

    }

    /**
     * Oauth请求
     * https://api.weibo.com/oauth2/authorize
     */
    def oauth = {
        //判断是否有cookie存在，并且cookie合法
        def uid = cookieService.getCookie("sleep_weibo_uid", request)
        def token = cookieService.getCookie("sleep_weibo_token", request)
        if (uid && token) {//判断用户是否存在
            WeiboUser wu = WeiboUser.findByUid(String.valueOf(uid))
            if (wu && token.equals(encodeSleepToken(wu.uid, wu.accessToken))) {
                redirect(action: "features")
                return
            }
        }
        def url = "https://api.weibo.com/oauth2/authorize?client_id=" + appId + "&redirect_uri=http://www.onlysleep.net/weibo/accessToken&scope=all"
        redirect(url: url)
    }
    /**
     * 根据code获取accessToken
     */
    def accessToken = {
        def code = params.code
        if (code) {
            def host = 'https://api.weibo.com'
            def uri = '/oauth2/access_token'
            Map par = [client_id: appId, client_secret: appSecret, grant_type: "authorization_code", code: params.code, redirect_uri: "http://www.onlysleep.net/weibo/accessToken"]
            def result = weiboHttpsService.post(host, uri, par)
            log.info result
            if (request != '访问失败') {
                JSONObject tokenJson = JSONObject.parseObject(result)
                log.info tokenJson.toString()
                def user = WeiboUser.findByUid(tokenJson.getString("uid"))
                def overduTime = new Date()
                use(TimeCategory) {
                    int second = tokenJson.getIntValue("expires_in")
                    overduTime = overduTime + second.seconds
                }
                if (user) {
                    user.accessToken = tokenJson.getString("access_token")
                    user.lastTokenTime = new Date()
                    user.overdueTime = overduTime
                    user.save(flush: true)
                } else {
                    WeiboUser wu = new WeiboUser(accessToken: tokenJson.getString("access_token"),
                            lastTokenTime: new Date(),
                            overdueTime: overduTime,
                            uid: tokenJson.getString("uid"),
                            createTime: new Date())
                    wu.save(flush: true)
                }
                cookieService.setCookie("sleep_weibo_uid", tokenJson.getString("uid"), response)
                cookieService.setCookie("sleep_weibo_token", encodeSleepToken(tokenJson.getString("uid"), tokenJson.getString("access_token")), response)
                redirect(action: "features")
            } else {
                render result
            }
        } else {
            log.error "授权失败:" + params.toString()
            flash.message = "授权失败！"
            redirect(uri: "/")
        }
    }
    /**
     * 获取微博timeline
     * 返回数据根据id降序
     * @see http://open.weibo.com/wiki/2/statuses/home_timeline
     * @return JSON
     */
    def getWeiboLine = {
        JSONObject json = new JSONObject()
        def host = "https://api.weibo.com"
        def uri = "/2/statuses/home_timeline.json"
        def page = params.page ? params.page : 1
        def count = params.count ? params.count : 20
        def feature = params.feature ? params.feature : 0
        def passed = false
        //判断是否有cookie存在，并且cookie合法
        def uid = cookieService.getCookie("sleep_weibo_uid", request)
        def token = cookieService.getCookie("sleep_weibo_token", request)
        def wu = new WeiboUser()
        if (uid && token) {//判断用户是否存在
            wu = WeiboUser.findByUid(String.valueOf(uid))
            if (wu && token.equals(encodeSleepToken(wu.uid, wu.accessToken))) {
                passed = true
            }
        }
        if (passed) {
            def access_token = wu.accessToken
            def map = [access_token: access_token, feature: feature, count: count, page: page]
            def result = ""
            try {
                result = weiboHttpsService.get(host, uri, map)
            } catch (Exception e) {
                e.properties
                json.put("status", "-1")
            }
            if (result != '访问失败') {
                JSONObject content = JSONObject.parseObject(result)
                JSONArray array = content.get("statuses")
                array.sort { a, b ->
                    return a.id > b.id ? 1 : -1
                }
                json.put("status", 1)
                json.put("weibos", array)
            } else {
                json.put("status", -1)
            }
        } else {
            json.put("status", 0)
        }
        render json as JSON
    }

    /**
     * Sleep微博Token加密
     * @param uid
     * @param token
     * @return
     */
    def encodeSleepToken(String uid, String token) {
        return ("{" + uid + "}" + token).encodeAsMD5()
    }

    def features = {

    }
    /**
     * 获取用户收藏
     */
    def getFeatures = {
        JSONObject json = new JSONObject()
        def uid = cookieService.getCookie("sleep_weibo_uid", request)
        def token = cookieService.getCookie("sleep_weibo_token", request)
        if (uid && token) {//判断用户是否存在
            WeiboUser wu = WeiboUser.findByUid(String.valueOf(uid))
            //授权通过，使用uid查询
            if (wu && token.equals(encodeSleepToken(wu.uid, wu.accessToken))) {
                params.max = Math.min(params.max ? params.int('max') : 10, 100)
                params.offset = params.offset ? params.int('offset') : 0
                params.page = params.page ? params.int('page') : 1
                def query = {
                    eq("commentUserId", uid)
                }
                def count = WeiboCollect.createCriteria().count(query)
                def list = WeiboCollect.createCriteria().list(params, query)
                json.put("status", 1)
                json.put("count", count)
                json.put("page", params.page)
                json.put("list", list)
                def result = json as JSON
                render result
                return
            }
        }
        json.put("status", "-1")
        json.put("msg", "获取失败，请<a href='/weibo/oauth'>重新授权</a>")
        render json as JSON
    }


    def about = {

    }
    /**
     * 计算人意两个ID之间的关系
     *
     * 借助m.weibo.cn实现
     *
     * 根据关注关系计算
     */
    def getRelation = {
        JSONObject result = new JSONObject()
        def startName = params.startName
        def endName = params.endName
        log.info "起始微博昵称" + startName
        log.info "目标博昵称" + endName
        if (startName && endName) {
            List<WeiboTree> fans = new ArrayList<WeiboTree>()
            getFansData(endName, 0, fans, null)
            def nearTree = contains(fans, startName as String)
            if (nearTree) {
                log.info(nearTree)
                //计算路径...
                //返回结果
                result.put("status", 200)
            } else {
                result.put("status", 404)
            }

        } else {
            result.put("status", 400)
        }
        render result.toString()
    }

    /**
     *
     * @param endName
     * @param deep
     * @param trees
     * @return
     */
    def getFansData(def endName, int deep,def trees, WeiboTree parentNode) {
        try {
            deep++
            //获取startName
            def url = baseUrl + endName
            //获取首页的第三个node
            def node = getUserUrl(endName, 3)
            if (node && deep < MAX_DEEP) {
                node = node as Document
                def fansCount = Integer.valueOf(node.select(".mct-a").get(0).text())
                def fansUrl = node.attr("href") + "&page={page}"
                def emptyQuery = 0//如果连续查询10次仍然是空就不要继续查询了...
                //新浪微博接口限制，未登陆状态下，page=2必然返回无信息...
                def page = 0
                def ownIndex = trees.size()
                if (parentNode == null) {//第一次遍历必然为null
                    ownIndex++
                    parentNode = new WeiboTree(
                            ownIdx: ownIndex,
                            parentIdx: null,
                            childNodIds: new ArrayList<String>(),
                            deep: deep,
                            nickName: endName
                    )
                    trees.add(parentNode)
                }

                while (emptyQuery < 11 && fansCount > 0) {
                    def queryJson = new URL(fansUrl.replace("{page}", page)).getText()
                    def validJson = validJson(queryJson)
                    if (validJson) {
                        validJson = validJson as JSONObject
                        if (validJson.get("count") != null) {
                            JSONArray fansArray = validJson.getJSONObject("cards").getJSONArray("card_group")
                            fansArray.each { ele ->
                                ownIndex++
                                //组装WeiboTree
                                WeiboTree child = new WeiboTree(
                                        ownIdx: ownIndex,
                                        parentIdx: parentNode.ownIdx,
                                        childNodIds: new ArrayList<String>(),
                                        deep: deep,
                                        nickName: (ele as JSONObject).getJSONObject("user").get("screen_name")
                                )
                                trees.add(child)
                                //更新parent的childNodIds
                                trees.get(parentNode.ownIdx).childNodIds.add(child.ownIdx)
                                //继续递归
                                getFansData(child.nickName, deep, trees, child)
                            }
                        } else {
                            emptyQuery++
                        }

                    }
                    page++
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
            return e.getMessage()
        }
    }

    /**
     * 获取首页4个链接
     * 0:详细信息
     * 1：微博
     * 2：关注
     * 3：粉丝
     * @param name
     * @param type
     * @return document
     */
    def getUserUrl(String name, Integer type) {
        def url = baseUrl + name
        log.info url
        Document document = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").get()
        Elements eles = document.select(".card-list>.line-around>.layout-box")
        int count = 0
        def node = ""
        eles.each {
            ele ->
                if (count == type) {
                    node = ele
                }
        }
        return node
    }
    /**
     * 判定text是否是json
     * 正常返回JsonObject
     * 异常返回false
     * @param text
     */
    def validJson(String text) {
        try {
            return JSONObject.parseObject(text)
        } catch (Exception ex) {
            return false
        }
    }

    def contains(def trees, String name) {
        WeiboTree nearTree = null
        for (WeiboTree tree : trees) {
            if (tree.nickName == name) {
                if (nearTree == null) {
                    nearTree = tree
                } else {//比较deep
                    if (nearTree.deep > tree.deep) {
                        nearTree = tree
                    }
                }


            }
        }
        return nearTree
    }


}
