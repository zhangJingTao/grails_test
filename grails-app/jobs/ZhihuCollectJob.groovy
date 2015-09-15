import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * 知乎Job 每天抓两次
 */
class ZhihuCollectJob {
    static triggers = {
        cron(name: 'zhihuCollect',cronExpression: '0 0 7/11 * * ?')
//        cron(name: 'zhihuCollect',cronExpression: '0 0/1 * * * ?')
    }

    def execute() {
        def list = ZhihuCollect.findAllByEnabled(true).asList()
        def host = "http://www.zhihu.com/collection/"
        def zhihu = "http://www.zhihu.com"
        def lastTitle = ""//同问题多个收藏的情况
        list.each {
            c ->
                def url = host + c.collectId
                Document document = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.94 Safari/537.36")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,fr;q=0.2")
                        .header("Cache-Control", "no-cache")
                        .header("Connection", "keep-alive")
                        .timeout(100000)
                        .get();
                Elements eles = document.allElements
                eles = eles.select("#zh-list-answer-wrap").select(".zm-item")
                eles.each {
                    ele ->
                        try {
                            def titleContent = ele.select(".zm-item-title")?.get(0)
                            def voteContent = ele.select(".zm-item-fav").select(".zm-item-vote")
                            def authorContent = ele.select(".zm-item-fav").select(".answer-head").select(".zm-item-answer-author-info").select(".zm-item-answer-author-wrap").get(0)
                            def commentContent = ele.select(".zm-item-fav").select(".zm-item-meta").select(".zm-meta-panel").select(".toggle-comment").get(0)

                            def title = titleContent==null? lastTitle:titleContent.select("a").text()
                            lastTitle = title
                            def questionUrl = zhihu + titleContent.select("a").attr("href")
                            def vote = Integer.valueOf(voteContent.select("a").text())
                            def author = authorContent.select("a").text()
                            def authorDesc = authorContent.select("strong").text()
                            def content = ele.select(".zm-item-fav").select(".zm-item-rich-text").select(".content").get(0).text()
                            def createdDate = new Date()
                            def zhihuId = ele.select(".zm-item-fav").select(".zm-item-rich-text").get(0).attr("data-resourceid")
                            Integer viewTime = 0
                            Integer commentTimes = Integer.parseInt(commentContent.text().replace(" 条评论", ""))
                            ZhihuCollectContent zcc = new ZhihuCollectContent(
                                    title: title,
                                    questionUrl: questionUrl,
                                    vote: vote,
                                    author: author,
                                    authorDesc: authorDesc,
                                    content: content.replaceAll("http://pic[0-9].zhimg.com","/zhihu/pic?url=").replaceAll("//pic[0-9].zhimg.com","/zhihu/pic?url=").replaceAll("pic[0-9].zhimg.com","/zhihu/pic?url="),
                                    createdDate: createdDate,
                                    viewTime: viewTime,
                                    commentTimes: commentTimes,
                                    zhihuId: zhihuId,
                                    enabled: true
                            )
                            zcc.collect = c

                            if (zcc.save(flush: true)) {
                            } else {
                                log.info zcc.title + "已存在"
                            }
                        } catch (Exception e) {
                            log.info "抓取失败"
                        }
                }
        }
    }
}
