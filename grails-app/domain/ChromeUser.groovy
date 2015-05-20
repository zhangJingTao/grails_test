/**
 * Chrome用户
 */
class ChromeUser {

    static constraints = {
        email(unique: true,email: true)
    }

    String userToken//匿名数据 token
    String accessToken//访问数据 token
    String email//注册Email
    Date dateCreted //创建时间
    String source//来源

}
