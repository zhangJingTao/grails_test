/**
 * 抓到的收藏内容
 */
class ZhihuCollectContent{
    static constraints = {
        zhihuId(unique: true)
        authorDesc(nullable: true)
    }
    String zhihuId
    String title
    Date createdDate
    Integer viewTime
    Integer vote
    Integer commentTimes
    String author
    String authorDesc
    String questionUrl
    Boolean enabled
    ZhihuCollect collect
    ZhihuCollectContentExt ext

    String getValidContent() {
        if (ext) {
            //对于=== ---进行replace 通常是分割线
            return ext.getContent().replace("=======","").replace("-------","")
        }
    }

//    String getContent() {
//        return URLEncoder.encode(this.content,"UTF-8")
//    }
}
