import com.alibaba.fastjson.JSONObject

class RssEmailController {
    def index() {
        redirect(action: "/")
    }

    def rssAdd = {
        def email = params.email
        RssEmail rssEmail = new RssEmail(userEmail:email,state: 1,uuid: UUID.randomUUID().toString(),createDate: new Date())
        JSONObject result = new JSONObject()
        if (rssEmail.validate()){
            if (rssEmail.save(flush: true)){
                result.put("result","1")
            }
        }else{
            result.put("result","0")
        }
        render result.toString()
    }
}
