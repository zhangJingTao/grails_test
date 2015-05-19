/**
 * Chrome插件提供接口
 */
class ChromeController {

    def index() {}

    def saveData(){
        def token = params.token
        if (token){
            ChromeUser user = ChromeUser.findByUserToken(token)
            if (user){

            }
        }
    }
}
