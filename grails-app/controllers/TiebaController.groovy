import groovy.sql.Sql
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class TiebaController {
    def dataSource

    def index = {
        def db = new Sql(dataSource)
        def date = new Date().format("yyyy-MM-dd 00:00:00")
        List<Integer> totals = new ArrayList<Integer>()
        List<Integer> valids = new ArrayList<Integer>()
        db.eachRow("""SELECT count(id) as num,DATE_FORMAT(date_created,'%y-%m-%d %H') FROM tieba_card WHERE date_created >'${date}' GROUP BY DATE_FORMAT(date_created,'%y-%m-%d %H') ORDER BY DATE_FORMAT(date_created,'%y-%m-%d %H')"""){
            rs->
                totals.add(rs.num as Integer)
        }
        db.eachRow("""SELECT count(id) as num,DATE_FORMAT(date_created,'%y-%m-%d %H') FROM tieba_card WHERE title is not null and date_created >'${date}' GROUP BY DATE_FORMAT(date_created,'%y-%m-%d %H') ORDER BY DATE_FORMAT(date_created,'%y-%m-%d %H')"""){
            rs->
                valids.add(rs.num as Integer)
        }
        [total:totals,valid:valids]
    }

    def getIt = {
        def startNum = params.start ? params.start as Long : 3924777600L
        def endNum = params.end ? params.end as Long : 3924777643L
        def baseUrl = "http://tieba.baidu.com/p/"
        for (Long i = startNum; i < endNum; i++) {
            log.info("cur tieba_url:" + baseUrl + "${i}")
            def url = baseUrl + "${i}"
            def title = ""
            def author = ""
            def count = ""
            def source = ""
            def createDate = ""
            try {
                Document document = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").get()
                title = document.select(".core_title_txt")?.get(0)?.text()
                log.info("title:${title}")
                author = document.select(".p_author_name ")?.get(0)?.text()
                log.info("author:${author}")
                count = document.select(".l_reply_num")?.get(0)?.select("span")?.get(0)?.text()
                log.info("count:${count}")
                source += document.select(".tail-info")?.get(0)?.text()+"&&"
                source += document.select(".tail-info")?.get(1)?.text()+"&&"
                source += document.select(".tail-info")?.get(2)?.text()+"&&"
                if (document.select(".tail-info").size()>3){
                    source += document.select(".tail-info")?.get(3)?.text()+"&&"
                }
                source.split("&&").each {ele->
                    if (ele.length()==16){
                        createDate = ele
                    }
                }
                log.info("source:${source}")
                TiebaCard card = new TiebaCard(
                        curNum: i,
                        title: title,
                        author: author,
                        url: url,
                        status: 200,
                        replyCount: count,
                        createDate: createDate,
                        dateCreated: new Date(),
                        source: source
                )
                card.save(flush: true)
            } catch (Exception e) {
                log.error("${url}帖子可能不存在...")
                TiebaCard card = new TiebaCard(
                        title: title,
                        author: author,
                        url: url,
                        status: 404,
                        replyCount: 0,
                        createDate: createDate,
                        dateCreated: new Date(),
                        curNum: i
                )
                card.save(flush: true)
                continue
            }
        }
    }
}
