/**
 * 微博自动回复内容
 */
class WeiboAutoReply {

    static constraints = {
    }
    String weiboId//微博ID
    String replyText//处理后数据
    String weiboUserId//用户id
    String replyContent//接口返回数据
    Date createDate
    String replyType//类型//文字：100000 链接：200000 新闻：302000 列车：305000 航班：306000 菜谱：308000
}
