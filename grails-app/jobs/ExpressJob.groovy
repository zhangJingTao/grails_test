//import com.alibaba.fastjson.JSONObject
//import org.codehaus.groovy.grails.commons.ApplicationHolder
//import org.codehaus.groovy.grails.commons.GrailsApplication
//import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
//import org.codehaus.groovy.grails.plugins.web.taglib.RenderTagLib
//
///**
// * 快递自动更新任务
// */
//class ExpressJob{
//    def sendMailService
//    def expressService
//    def freemarkerConfig
//    static  triggers = {
//        cron(name: 'expressJob',cronExpression: '0 0/30 * * * ?')
//    }
//
//    def execute(){
//        //查询所有未check && 需要提醒的
//        def list = ExpressQuartz.findAllByNotificationAndUnChecked(true,true)
//        list.each {
//            quartz ->
//                def no = quartz.expressNo
//                def email = quartz.notiEmail
//                def times = quartz.times
//                def company = quartz.company
//                def lastNu = quartz.lastNu
//                def lastData = JSONObject.parseObject(quartz.lastQueryJson).getJSONArray("data").toString()
//                def queryStr = expressService.query(no,company)
//                JSONObject json = JSONObject.parseObject(queryStr)
//                if (json.get("message")=='ok'){
//                    //判定是否已check
//                    quartz.lastUpdate = new Date()
//                    quartz.times = times+1
//                    quartz.lastQueryJson
//                    quartz.lastNu = json.getString("updatetime")
//                    quartz.unChecked = true
//                    if (json.get("ischeck") == '1'){
//                        quartz.unChecked = false
//                    }
//                    quartz.save(flush: true)
//                    if (lastData != json.getJSONArray("data").toString()){//有新动态
//                        SendMailService sms = new SendMailService()
//                        HashMap<String,Object> map = new HashMap<String,Object>()
//                        FreemarkerUtils.initFreeMarker(freemarkerConfig.getConfiguration());
//                        def array = json.get("data")
//                        map.put("date",quartz.createDate.format("yyyy-MM-dd HH:mm:ss"))
//                        map.put("times",quartz.times)
//                        map.put("list",array)
//                        map.put("id",quartz.id)
//                        map.put("key",quartz.notificationKey)
//                        File outPutFile = FreemarkerUtils.crateFile(map,"/express/expressMail.ftl","test.html",false)
//                        log.info outPutFile.getAbsolutePath()
//                        sms.SendEmailAsynchronously(quartz.notiEmail,"快递"+quartz.expressNo+"有新的动态",outPutFile)
//                        sms.sendMail()
//                        ExpressQuartzEmailLog log = new ExpressQuartzEmailLog(
//                                quartz: quartz,
//                                title: "快递"+quartz.expressNo+"有新的动态",
//                                content: array.toString(),
//                                sendTime: new Date()
//                        )
//                    }
//                }else {
//                    log.info queryStr
//                }
//        }
//
//    }
//}
