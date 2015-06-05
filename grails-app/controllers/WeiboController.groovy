import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import grails.converters.JSON
import groovy.time.TimeCategory
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

    def index() {
        if (!params.passed) {
            redirect(action: "oauth")
        }else {
            redirect(action: "home")
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
        if (uid&&token) {//判断用户是否存在
            WeiboUser wu = WeiboUser.findByUid(String.valueOf(uid))
            if (wu && token.equals(encodeSleepToken(wu.uid, wu.accessToken))) {
                redirect(action: "home")
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
                cookieService.setCookie("sleep_weibo_uid", tokenJson.getString("uid"),response)
                cookieService.setCookie("sleep_weibo_token", encodeSleepToken(tokenJson.getString("uid"), tokenJson.getString("access_token")),response)
                redirect(action: "home")
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
        def page = params.page? params.page:1
        def count = params.count? params.count:100
        def feature = params.feature? params.feature:0
        def passed = false
        //判断是否有cookie存在，并且cookie合法
        def uid = cookieService.getCookie("sleep_weibo_uid", request)
        def token = cookieService.getCookie("sleep_weibo_token", request)
        def wu = new WeiboUser()
        if (uid&&token) {//判断用户是否存在
            wu = WeiboUser.findByUid(String.valueOf(uid))
            if (wu && token.equals(encodeSleepToken(wu.uid, wu.accessToken))) {
                passed = true
            }
        }
        if (passed){
            def access_token = wu.accessToken
            def map = [access_token:access_token,feature:feature,count:count,page:page]
            def result = ""
            try {
                result = weiboHttpsService.get(host,uri,map)
            }catch (Exception e){
                e.properties
                json.put("status","-1")
            }
            if (result!='访问失败'){
                JSONObject content = JSONObject.parseObject(result)
                JSONArray array = content.get("statuses")
                array.sort {a,b ->
                    return a.id>b.id?1:-1
                }
                json.put("status",1)
                json.put("weibos",array)
            }else {
                json.put("status",-1)
            }
        }else {
            json.put("status",0)
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
}
