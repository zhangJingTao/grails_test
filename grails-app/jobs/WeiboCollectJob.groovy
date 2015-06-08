import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import groovy.sql.Sql
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils

class WeiboCollectJob {
    static  triggers = {
        cron(name: 'weiboCollect',cronExpression: '0 0/10 * * * ?')
    }
    def dataSource
    def weiboHttpsService

    def lastWeiboId = null
    def properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource('app.properties'))
    def appId = properties.getProperty("appKey")
    def appSecret = properties.getProperty("appSecret")


    def execute(){
        def runSuccess = false
        def user = WeiboUser.findByUid("2287744875")
        if (user){
            if (!lastWeiboId){
                def db = new Sql(dataSource)
                def sql = "SELECT last_weibo_id as id from weibo_job_history c where c.successed=1 order by c.run_date desc"
                def result = db.firstRow(sql)
                if (result){
                    lastWeiboId = Long.valueOf(result.id)
                }else{
                    lastWeiboId = 0L
                }
            }
            if (user.overdueTime<new Date()){
                log.error "授权过期！"
                return;
            }
            def host = "https://api.weibo.com"
            def path = "/2/comments/mentions.json"
            def param = [access_token:user.accessToken,since_id:lastWeiboId,count:200]
            def json = weiboHttpsService.get(host,path,param)
            log.info "weibo-job:"+json
            if (json!='访问失败'){
                JSONObject content = JSONObject.parseObject(json)
                JSONArray array = content.get("comments")
                array.sort {a,b ->
                    return a.id>b.id? 1:-1
                }
                if (array.size()>0){
                    array.each {obj->
                        lastWeiboId = obj.id
                        def wb = obj.status
                        WeiboCollect wc = new WeiboCollect(
                                commentDate: obj.created_at,
                                comments: obj.text,
                                commentUserId: obj.user.id,
                                commentUserName: obj.user.name,
                                weiboCreateDate:wb.created_at,
                                weiboId: wb.id,
                                weiboUserId: wb.user.id,
                                weiboUserName: wb.user.name,
                                text: wb.text,
                                createdDate: new Date(),
                                commentId: obj.id
                        )
                        if (wb.retweeted_status){
                            def reWb = wb.retweeted_status
                            wc.reText = reWb?.text
                            wc.reWeiboCreateDate = reWb?.created_at
                            wc.reWeiboId = reWb?.id
                            wc.reWeiboUserId = reWb?.user?.id
                            wc.reWeiboUserName = reWb?.user?.name
                        }
                        try{
                            wc.save(flush: true)
                        }catch (Exception e){
                            log.info "emoji:"+wc.comments
                        }
                        log.info "save collect:"+wc.id
                    }
                    runSuccess = true
                }
            }else {
               log.error json
            }

        }else {
            log.error "无accesstoken用户！！！！！"
        }

        WeiboJobHistory wjh = new WeiboJobHistory(
                lastWeiboId:lastWeiboId,
                runDate:new Date(),
                successed:runSuccess
        )
        wjh.save(flush: true)
    }
}
