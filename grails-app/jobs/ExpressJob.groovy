import com.alibaba.fastjson.JSONObject
import org.codehaus.groovy.grails.plugins.web.taglib.RenderTagLib

/**
 * 快递自动更新任务
 */
class ExpressJob {
    def sendMailService
    def expressService
    RenderTagLib g = new RenderTagLib()

    static  triggers = {
        cron(name: 'expressJob',cronExpression: '0 0/1 * * * ?')
    }

    def execute(){
        //查询所有未check && 需要提醒的
        def list = ExpressQuartz.findAllByNotificationAndUnChecked(true,true)
        list.each {
            quartz ->
                def no = quartz.expressNo
                def email = quartz.notiEmail
                def times = quartz.times
                def company = quartz.company
                def lastNu = quartz.lastNu
                def queryStr = expressService.query(no,company)
                JSONObject json = JSONObject.parseObject(queryStr)
                if (json.get("message")=='ok'){
                    //判定是否已check
                    quartz.lastUpdate = new Date()
                    quartz.times = times+1
                    quartz.lastQueryJson
                    quartz.lastNu = json.getString("nu")
                    quartz.unChecked = true
                    if (json.get("ischeck") == '1'){
                        quartz.unChecked = false
                    }
                    quartz.save(flush: true)
                    if (lastNu != quartz.lastNu){//有新动态
                        def array = json.get("data")
                        def start = quartz.createDate
                        SendMailService sms = new SendMailService()
                        sms.SendEmailAsynchronously("610039879@qq.com","快递"+quartz.expressNo+"有新的动态",g.render(template: 'expressMail',model: [list:array,date:start.format("yyyy-MM-dd HH:mm:ss"),times:times+1,id:quartz.id]))
                        sms.sendMail()
                        ExpressQuartzEmailLog log = new ExpressQuartzEmailLog(
                                quartz: quartz,
                                title: "快递"+quartz.expressNo+"有新的动态",
                                content: array.toString(),
                                sendTime: new Date()
                        )
                    }
                }else {
                    log.info queryStr
                }
        }

    }
}
