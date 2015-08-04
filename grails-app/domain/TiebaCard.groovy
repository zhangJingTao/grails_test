/**
 * 百度贴吧帖子内容抓取
 */
class TiebaCard {

    static constraints = {
        title(nullable: true)
        replyCount(nullable: true)
        author(nullable: true)
        source(nullable: true)
    }
    Long curNum
    String url
    String title
    String author
    Integer status
    Integer replyCount
    String createDate
    String source
    Date dateCreated
}
