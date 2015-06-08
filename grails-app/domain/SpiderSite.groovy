/**
 * 被爬网站
 */
class SpiderSite {

    static constraints = {
        siteUrl(unique: true)
        page(nullable: true)
    }

    String siteName
    String siteUrl
    Boolean enabled
    Integer page

}
