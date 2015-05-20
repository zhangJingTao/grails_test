import com.alibaba.fastjson.JSONObject

/**
 * Chrome插件提供接口
 */
class ChromeController {

    def index() {}

    static allowedMethods = [chromeReg: "GET", saveData: "POST"]
    /**
     * 保存用户浏览数据
     * @return
     */
    def saveData = {
        def token = params.token
        if (token && token != 'null') {
            ChromeUser user = ChromeUser.findByUserToken(token)
            if (user) {
                ChromeUserVisit userVisit = new ChromeUserVisit(user: user, dateCreted: new Date(), url: params.url, title: params.title, ip: request.getRemoteAddr(), userAgent: request.getHeader("User-Agent"))
                userVisit.save(flush: true)
            }
        } else {
            ChromeUserVisit userVisit = new ChromeUserVisit(dateCreted: new Date(), url: params.url, title: params.title, ip: request.getRemoteAddr(), userAgent: request.getHeader("User-Agent"))
            userVisit.save(flush: true)
        }
        render "{success:1}"
    }
    /**
     * 用户注册
     */
    def chromeReg = {
        def email = params.email
        def source = params.source
        //检查是否已有email
        ChromeUser user = ChromeUser.findByEmail(email);
        JSONObject json = new JSONObject()
        if (user) {//老用户
            json.put("result", 1)
            json.put("token", user.userToken)
        } else {
            ChromeUser cu = new ChromeUser(email: email, userToken: UUID.randomUUID().toString(), dateCreted: new Date(), source: source, accessToken: UUID.randomUUID().toString())
            if (cu.save(flush: true)) {
                json.put("result", 1)
                json.put("token", cu.userToken)
            } else {
                json.put("result", 0)
            }
        }
        render json.toString()
    }
}
