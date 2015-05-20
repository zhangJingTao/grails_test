/**
 * Chrome用户访问数据
 */
class ChromeUserVisit {

    static constraints = {
        user(nullable: true)
    }

    ChromeUser user
    Date dateCreted
    String url
    String title
    String ip
    String userAgent
}
