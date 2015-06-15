import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Created by admin on 2015/6/8.
 */
class SpiderJob {
    static  triggers = {
       cron(name: 'spiderJob',cronExpression: '0 0 3 * * ?')
    }

    def execute(){
        println "*"*50
        println "Start Collect List"
        println "*"*50
        //获取所有enabled的site
        def siteList = SpiderSite.findAllByEnabled(true)
        siteList.each {
            site ->
                log.warn "*"*50
                log.warn site.siteName+":"+site.siteUrl+":"+site.id
                log.warn "*"*50
                def configs = SpiderSiteSelectorConfig.findAllBySite(site)
                configs.sort{a,b ->
                    return a.sort-b.sort
                }
                def selector = ''
                configs.each {
                    config ->
                        selector += config.selector+">"
                }
                selector = selector.subSequence(0,selector.length()-1)
                def max = 1
                if (site.page){
                    max = site.page
                }
                for(def i=1;i<max+1;i++){
                    def url = site.siteUrl
                    if (site.siteUrl.indexOf("{page}")>0){
                        url = url.replace("{page}",i+"")
                    }
                    Document document = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.94 Safari/537.36")
                            .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                            .header("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,fr;q=0.2")
                            .header("Cache-Control","no-cache")
                            .header("Connection","keep-alive")
                            .get();
                    Elements eles = document.allElements
                    for (SpiderSiteSelectorConfig config:configs){
                        eles = eles.select(config.selector)
                    }
                    eles.each {
                        ele ->
                            SpiderNews sn = new SpiderNews(
                                    url: ele.attr("href"),
                                    title: ele.text(),
                                    site:  site,
                                    createDate: new Date()
                            )
                        log.warn "saving..."+sn.title
                        sn.save(flush: true)
                    }
                }
        }
        println "*"*50
        println "End Collect List"
        println "*"*50
    }
}
