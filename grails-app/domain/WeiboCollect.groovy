/**
 * Weibo用户
 */
class WeiboCollect {

    static constraints = {
        reWeiboCreateDate(nullable: true)
        reWeiboId(nullable: true)
        reText(nullable: true)
        reWeiboUserName(nullable: true)
        reWeiboUserId(nullable: true)
        comments(nullable: true)
        commentId(nullable: true)
    }
    static mapping = {
    }

    static hasMany = [imgs:WeiboCollectImg]

    /**
     * @的微博
     */
    String weiboCreateDate
    String weiboId
    String text
    String weiboUserName
    String weiboUserId
    /**
     * 如果是转发微博以下字段不为空
     */
    String reWeiboCreateDate
    String reWeiboId
    String reText
    String reWeiboUserName
    String reWeiboUserId
    /**
     * 评论相关
     */
    String comments//评论内容
    String commentUserId
    String commentUserName
    String commentDate

    Date createdDate
    String commentId
}
