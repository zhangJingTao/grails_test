/**
 * Rss订阅
 * @author ZhangJingtao
 */
class RssEmail {

    static constraints = {
        userEmail(email: true,unique: true,nullable: false)
        state(inList: [0,1])
        uuid(nullable: false)
    }
    static mapping = {

    }
    String userEmail
    Date createDate
    String uuid
    Integer state
}
