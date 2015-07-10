import com.alibaba.fastjson.JSONObject
import grails.converters.JSON

class ExpressController {
    def sendMailService
    def index() {
//        sendMailService.SendEmailAsynchronously("610039879@qq.com","fdjslkatest","fklslj21body!!!")
//        sendMailService.sendMail()
    }
    def expressService

    def queryComp = {
        def text = params.text
        def str = '{"comCode":"","num":"200093247451","auto":[{"comCode":"yuantong","id":"","noCount":25729,"noPre":"2000","startTime":""},{"comCode":"annengwuliu","id":"","noCount":11540,"noPre":"2000","startTime":""},{"comCode":"gtongsudi","id":"","noCount":2902,"noPre":"2000","startTime":""},{"comCode":"rufengda","id":"","noCount":379,"noPre":"2000","startTime":""},{"comCode":"lianhaowuliu","id":"","noCount":266,"noPre":"2000","startTime":""},{"comCode":"quanfengkuaidi","id":"","noCount":217,"noPre":"2000","startTime":""},{"comCode":"shunfeng","id":"","noCount":5,"noPre":"2000","startTime":""},{"comCode":"suer","id":"","noCount":5,"noPre":"2000","startTime":""}]}'
                //new URL("http://www.kuaidi100.com/autonumber/autoComNum?text="+text).getText()
        def json = JSONObject.parseObject(str)
        def auto = json.getJSONArray("auto")
        render auto as JSON
    }

    def joinIt = {
        def company = params.company
        def text = params.text
        def email = params.email
        //查询一次返回
        def queryStr = '{"message":"ok","nu":"200093247451","ischeck":"1","com":"yuantong","status":"200","condition":"F00","state":"3","data":[{"time":"2015-04-15 09:09:56","context":"客户 签收人 : 张 已签收","ftime":"2015-04-15 09:09:56"},{"time":"2015-04-15 09:03:00","context":"山东省潍坊市潍城区十笏园分部公司派件人 : 邹辉 派件中 派件员电话","ftime":"2015-04-15 09:03:00"},{"time":"2015-04-14 09:09:16","context":"山东省潍坊市潍城区十笏园分部公司 失败签收录入 于明鑫","ftime":"2015-04-14 09:09:16"},{"time":"2015-04-14 09:03:59","context":"山东省潍坊市潍城区十笏园分部公司派件人 : 邹辉 派件中 派件员电话","ftime":"2015-04-14 09:03:59"},{"time":"2015-04-14 07:15:57","context":"快件到达 山东省潍坊市潍城区十笏园分部公司","ftime":"2015-04-14 07:15:57"},{"time":"2015-04-14 05:50:25","context":"山东省潍坊市公司 已发出,下一站 山东省潍坊市潍城区十笏园分部","ftime":"2015-04-14 05:50:25"},{"time":"2015-04-14 04:07:09","context":"潍坊转运中心公司 已发出,下一站 山东省潍坊市","ftime":"2015-04-14 04:07:09"},{"time":"2015-04-11 19:30:06","context":"浙江省金华市义乌市凌云公司 已打包,发往下一站 潍坊转运中心","ftime":"2015-04-11 19:30:06"},{"time":"2015-04-11 17:58:41","context":"浙江省金华市义乌市凌云公司 已揽收","ftime":"2015-04-11 17:58:41"}]}'
                //expressService.query(text,company)
        def eq = ExpressQuartz.findAllByExpressNoAndCompany(text,company)
        if (eq.size()>0){
            render eq.get(0).lastQueryJson
            return
        }
        JSONObject json = JSONObject.parseObject(queryStr)
        ExpressQuartz quartz = new ExpressQuartz(
                expressNo:text,
                company:company,
                lastUpdate:new Date(),
                lastQueryJson:queryStr,
                lastNu:json.getString("nu"),
                unChecked:json.getBoolean("ischeck"),
                notiEmail:email
        )
        if (quartz.validate()){
            quartz.save(flush: true)
            render queryStr
        }else {
            render "error"
        }

    }
}
