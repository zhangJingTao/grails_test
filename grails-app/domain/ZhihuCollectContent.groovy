/**
 * 抓到的收藏内容
 */
class ZhihuCollectContent {

    static constraints = {
        zhihuId(unique: true)
        authorDesc(nullable: true)
    }
    String zhihuId
    String title
    String content
    Date createdDate
    Integer viewTime
    Integer vote
    Integer commentTimes
    String author
    String authorDesc
    String questionUrl
    ZhihuCollect collect

//    String getContent() {
//        return URLEncoder.encode(this.content,"UTF-8")
//    }
}
