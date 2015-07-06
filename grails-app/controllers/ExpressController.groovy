import com.alibaba.fastjson.JSONObject
import grails.converters.JSON

class ExpressController {
    def sendMailService
    def index() {
//        sendMailService.SendEmailAsynchronously("610039879@qq.com","fdjslkatest","fklslj21body!!!")
//        sendMailService.sendMail()
    }

    def queryComp = {
        def text = params.text
        def str = new URL("http://www.kuaidi100.com/autonumber/autoComNum?text="+text).getText()
        def json = JSONObject.parseObject(str)
        def auto = json.getJSONArray("auto")
        render auto as JSON
    }
}
