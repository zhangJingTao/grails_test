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
    Boolean enabled
    ZhihuCollect collect

    String getValidContent() {
        if (content) {
            //对于=== ---进行replace 通常是分割线
            return content.replace("=======","").replace("-------","")
        }
    }

//    String getContent() {
//        return URLEncoder.encode(this.content,"UTF-8")
//    }
}
