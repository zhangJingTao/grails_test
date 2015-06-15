import com.alibaba.fastjson.JSONObject
import grails.converters.JSON
import groovy.sql.Sql

class ZhihuController {
    def dataSource

    def index() {}

    def giveMeFive(){
        def minId = params.minId? params.getLong("minId"):1L
        def size = params.max? params.size:5
        def data = new Sql(dataSource)
        params.max = params.max ? params.int('max') : 5
        params.offset = 0
        def query = {
            ge("id",minId)
        }
        def list = ZhihuCollectContent.createCriteria().list(params,query)
        JSONObject json = new JSONObject()
        json.put("status","1")
        json.put("nexBaseId",1+(list.last() as  ZhihuCollectContent).id)
        json.put("list",list)
        render json as JSON
    }
}
