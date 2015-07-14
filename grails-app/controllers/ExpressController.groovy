import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import grails.converters.JSON

class ExpressController {
    def sendMailService
    def freemarkerConfig
    def index() {
//        sendMailService.SendEmailAsynchronously("610039879@qq.com","fdjslkatest","fklslj21body!!!")
//        sendMailService.sendMail()
    }
    def expressService

    def queryComp = {
        def text = params.text
//        '{"comCode":"","num":"200093247451","auto":[{"comCode":"yuantong","id":"","noCount":25729,"noPre":"2000","startTime":""},{"comCode":"annengwuliu","id":"","noCount":11540,"noPre":"2000","startTime":""},{"comCode":"gtongsudi","id":"","noCount":2902,"noPre":"2000","startTime":""},{"comCode":"rufengda","id":"","noCount":379,"noPre":"2000","startTime":""},{"comCode":"lianhaowuliu","id":"","noCount":266,"noPre":"2000","startTime":""},{"comCode":"quanfengkuaidi","id":"","noCount":217,"noPre":"2000","startTime":""},{"comCode":"shunfeng","id":"","noCount":5,"noPre":"2000","startTime":""},{"comCode":"suer","id":"","noCount":5,"noPre":"2000","startTime":""}]}'
        def str = new URL("http://www.kuaidi100.com/autonumber/autoComNum?text="+text).getText()
        def json = JSONObject.parseObject(str)
        def auto = json.getJSONArray("auto")
        render auto as JSON
    }

    def joinIt = {
        def company = params.company
        def text = params.text
        def email = params.email
        //查询一次返回
//        '{"message":"ok","nu":"200093247451","ischeck":"1","com":"yuantong","status":"200","condition":"F00","state":"3","data":[{"time":"2015-04-15 09:09:56","context":"客户 签收人 : 张 已签收","ftime":"2015-04-15 09:09:56"},{"time":"2015-04-15 09:03:00","context":"山东省潍坊市潍城区十笏园分部公司派件人 : 邹辉 派件中 派件员电话","ftime":"2015-04-15 09:03:00"},{"time":"2015-04-14 09:09:16","context":"山东省潍坊市潍城区十笏园分部公司 失败签收录入 于明鑫","ftime":"2015-04-14 09:09:16"},{"time":"2015-04-14 09:03:59","context":"山东省潍坊市潍城区十笏园分部公司派件人 : 邹辉 派件中 派件员电话","ftime":"2015-04-14 09:03:59"},{"time":"2015-04-14 07:15:57","context":"快件到达 山东省潍坊市潍城区十笏园分部公司","ftime":"2015-04-14 07:15:57"},{"time":"2015-04-14 05:50:25","context":"山东省潍坊市公司 已发出,下一站 山东省潍坊市潍城区十笏园分部","ftime":"2015-04-14 05:50:25"},{"time":"2015-04-14 04:07:09","context":"潍坊转运中心公司 已发出,下一站 山东省潍坊市","ftime":"2015-04-14 04:07:09"},{"time":"2015-04-11 19:30:06","context":"浙江省金华市义乌市凌云公司 已打包,发往下一站 潍坊转运中心","ftime":"2015-04-11 19:30:06"},{"time":"2015-04-11 17:58:41","context":"浙江省金华市义乌市凌云公司 已揽收","ftime":"2015-04-11 17:58:41"}]}'
        def eq = ExpressQuartz.findAllByExpressNoAndCompany(text,company)
        if (eq.size()>0){
            render eq.get(0).lastQueryJson
            return
        }
        def queryStr = expressService.query(text,company)

        JSONObject json = JSONObject.parseObject(queryStr)
        def unChecked = true
        if (json.get("ischeck") == '1'){
            unChecked = false
        }
        ExpressQuartz quartz = new ExpressQuartz(
                expressNo:text,
                company:company,
                lastUpdate:new Date(),
                lastQueryJson:queryStr,
                lastNu:json.getString("nu"),
                unChecked:unChecked,
                notiEmail:email,
                notification:true,
                times:1,
                createDate: new Date()
        )
        if (quartz.validate()){
            quartz.save(flush: true,failOnError: true)
            render queryStr
        }else {
            log.error quartz.errors.toString()
            render "error"
        }
    }

    def unnoti = {
        def id = params.id
        ExpressQuartz quartz = ExpressQuartz.get(id)
        quartz.notification = false
        if (quartz.save(flush: true)){
            render "快递号:"+quartz.expressNo+"不会再收到动态提醒！/(ㄒoㄒ)/~~"
        }else{
            render "系统异常/(ㄒoㄒ)/~~"
        }
    }

    def test = {
//        ExpressMailContentGenerator generator = new ExpressMailContentGenerator()
//        def content = generator.getContent("C:\\git_work\\sleep-site\\grails-app\\views\\express\\expressMail.ftl","fdsafdsa")
        ExpressQuartz quartz = ExpressQuartz.get(1)
        JSONObject json = JSONObject.parseObject(quartz.lastQueryJson)
        def array = json.get("data")
        def start = quartz.createDate
        def times = quartz.times
        SendMailService sms = new SendMailService()
        HashMap<String,Object> map = new HashMap<String,Object>()
        FreemarkerUtils.initFreeMarker(freemarkerConfig.getConfiguration());
        map.put("date",quartz.createDate.format("yyyy-MM-dd HH:mm:ss"))
        map.put("times",quartz.times)
        map.put("list",array)
        map.put("id",quartz.id)
        File outPutFile = FreemarkerUtils.crateFile(map,"/express/expressMail.ftl","test.html",false)
        log.info outPutFile.getAbsolutePath()
        sms.SendEmailAsynchronously("610039879@qq.com","测试123",outPutFile)
        sms.sendMail()
    }
}
