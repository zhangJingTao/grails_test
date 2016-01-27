import com.alibaba.fastjson.JSONObject
import grails.converters.JSON
import groovy.sql.Sql

class ZhihuController {
    def dataSource

    def index() {
        [baseId:params.baseId? params.baseId:999999]
    }

    def giveMeFive(){
        def minId = params.minId? params.getLong("minId"):Integer.MAX_VALUE
        params.max = params.max ? params.int('max') : 5
        params.offset = 0
        def query = {
            le("id",minId)
            order("id","desc")
//            eq("enabled",true)
        }
        def list = ZhihuCollectContent.createCriteria().list(params,query)
        JSONObject json = new JSONObject()
        json.put("status","1")
        if (list.size()!=0){
            json.put("nexBaseId",(list.get(list.size()-1) as  ZhihuCollectContent).id-1)
        }else{
            json.put("nexBaseId",0)
        }
        json.put("list",list)
        render json as JSON
    }

    def del = {
        def id = params.id
        def zhihu = ZhihuCollectContent.get(id)
        if (zhihu){
            zhihu.enabled = false
            zhihu.save(flush: true)
        }
        render ""
    }
/**
 * 处理知乎防盗链 http://pic4.zhimg.com/90e3e56170e63620d7cfa8afb5ce3d0b_b.jpg
 */
    def pic = {
        def url = params.url
        def out = response.outputStream
        out << new URL("http://pic4.zhimg.com/"+url).openStream()
    }
    /*
     *
     * 查看详情
     */
    def detail = {
        def id = params.getLong("id")
        if (id){
            def content = ZhihuCollectContent.get(id)
            try {
                content.viewTime++//记录阅读次数
                content.save(flush: true)
            }catch (Exception ex){
                ex.printStackTrace()
            }
            [content:content]
        }else {
            redirect(action: "index")
        }
    }
    /**
     * 降序查看下一篇文章
     */
    def next(){
        def id = params.id
        if (id){
            def db = new Sql(dataSource)
            def sql = "select max(id) as id from zhihu_collect_content z where z.id<"+id
            def result = db.firstRow(sql)
            log.info result.id
            redirect(action: "detail",params: ["id":result.id as Long])
        }
    }
}
