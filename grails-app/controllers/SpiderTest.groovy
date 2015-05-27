import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Created by admin on 2015/5/27.
 */
class SpiderTest {
    public static void main(String[] args) {
//        openSite()
        println "输入zhihu账号密码，格式: userName||password"
        Scanner s = new Scanner(System.in)
        while (s.hasNext()){
            String line = s.nextLine();
            zhihu(line.split("\\|\\|")[0],line.split("\\|\\|")[1])
        }
    }

    def openSite(){
        Document document = Jsoup.connect("http://www.oschina.net/").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").get()
        Elements eles = document.select(".TodayNews>.p1>.today")
        eles.each {
            ele ->
                println ele.html()
        }
    }

    static void zhihu(String userName,String passWd){
        Connection.Response res = Jsoup.connect("http://www.zhihu.com/login").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
                .data("email", userName, "password", passWd)
                .method(Connection.Method.POST)
                .execute();
        Document doc = res.parse();

        String _xsrf = res.cookie("_xsrf");
        String q_c1 = res.cookie("q_c1");
        String z_c0 = res.cookie("z_c0");

        Document document = Jsoup.connect("http://www.zhihu.com/").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0")
                .cookie("_xsrf", _xsrf)
                .cookie("q_c1", q_c1)
                .cookie("z_c0", z_c0)
                .get();
        Elements eles = document.select(".feed-main")
        eles.each {
            ele ->
                println "url:"
                println document.baseUri()+ele.select(".content>h2>.question_link").attr("href")
                println "title:"
                println ele.select(".content>h2>.question_link").text()
        }
    }
}
