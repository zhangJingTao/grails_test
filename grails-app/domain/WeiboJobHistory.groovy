/**
 * Weibo用户
 */
class WeiboJobHistory {

    static constraints = {
    }

    String lastWeiboId
    Date runDate
    Boolean successed
    Integer jobType//0:@收藏 1：@微博
}
