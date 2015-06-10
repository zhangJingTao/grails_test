import com.alibaba.fastjson.JSONObject
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.apache.commons.io.IOUtils
import org.apache.http.conn.EofSensorInputStream
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.ssl.X509HostnameVerifier
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import static groovyx.net.http.ContentType.JSON

/**
 * 发送http请求
 */
class TuLingHttpService {
    def properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource('app.properties'))
    def appKey = properties.getProperty("tuling.appKey")

    /**
     * post
     * @param host
     * @param path
     * @param params
     * @return
     */
    String post(String host, String path, Map params,method = Method.POST,type = ContentType.JSON) {
        def http = new HTTPBuilder(host)
        http.handler.failure = { resp -> println "Failure: ${resp.statusLine}" }
        http.request(method, type) { req ->
            uri.path = path
            //传递的参数,将要按照你所指定的格式进行发送
            uri.query = params
            headers.'User-Agent' = 'Mozilla/5.0'
            println uri.query
            req.getParams().setParameter("http.connection.timeout", new Integer(60000));
            req.getParams().setParameter("http.socket.timeout", new Integer(60000));
            response.success = { resp, json ->
                if (type == JSON){
                    return json
                }
                String res = IOUtils.toString(json as EofSensorInputStream, "UTF-8")
                if (res) {
                    return res
                } else {
                    return "访问失败"
                }

            }
            response.failure = { resp ->
                println resp
                return "访问失败"
            }
        }
    }

    /**
     * get
     * @param host
     * @param path
     * @param params
     * @return
     */
    String get(String host,String path,Map param,type = ContentType.JSON) {
        return post(host,path,param,Method.GET,type)
    }


    /**
     * 处理图灵返回的数据
     * @param json
     */
    def dataFliter(JSONObject json){
        try {
            def code =json.getString("code")
            if (code.equals("100000")){//文本
                return json.getString("text")
            }
            if (code.equals("200000")){
                return "你要的东西在这里:"+json.getString("url")
            }
            if (code.equals("302000")){//新闻
                def list = json.getJSONArray("list")
                def res = ''
                list.each {obj ->
                    res += obj.article+"/"
                }
                return res
            }
            if (code.equals("305000")){//火车
                def list = json.getJSONArray("list")
                def res = ''
                list.each {obj ->
                    res += obj.trainnum+"["+obj.starttime+"-"+obj.endtime+"]"
                }
                return res
            }
            if (code.equals("306000 ")){//航班
                return "航班信息在这里:"+json.getString("url")
            }
            if (code.equals("308000")){
                return "我哪懂得做菜，摔！"
            }
        }catch (Exception e){
            return "处理时发现了一个问题...."+e
        }
    }
}
