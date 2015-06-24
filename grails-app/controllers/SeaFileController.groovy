import com.alibaba.fastjson.JSONObject

class SeaFileController {
    def index() {}
    /**
     *  注册
     */
    def reg = {
        def email = params.email
        def seafileAdmin = SysConfig.findByConfigKey("sea_file_admin").configValue
        def seafilePass = SysConfig.findByConfigKey("sea_file_pass").configValue
        def seaServerUrl = SysConfig.findByConfigKey("sea_file_serverurl").configValue
        def command = "curl -d \"username="+seafileAdmin+"&password="+seafilePass+"\" "+seaServerUrl+"/api2/auth-token/"
        def result = command.execute().text
        def json = JSONObject.parse(result) as JSONObject
        def token = json.get("token")
        def pwd = UUID.randomUUID().toString().substring(0,7)
        def regCommand = "curl -v -X PUT -d \"password="+pwd+"\" -H \"Authorization: Token "+token+"\" -H 'Accept: application/json; indent=4' "+seaServerUrl+"/api2/accounts/"+email+"/"
        print regCommand.execute().text
        render pwd
    }
}
