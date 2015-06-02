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
import static groovyx.net.http.ContentType.TEXT

/**
 * 发送https请求
 */
class WeiboHttpsService {
    def properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource('app.properties'))
    def appId = properties.getProperty("appKey")
    def appSecret = properties.getProperty("appSecret")

    /**
     * post
     * @param host
     * @param path
     * @param params
     * @return
     */
    String post(String host, String path, Map params,method = Method.POST,type = ContentType.JSON) {
        def nullTrustManager = [
                checkClientTrusted: { chain, authType -> },
                checkServerTrusted: { chain, authType -> },
                getAcceptedIssuers: { null }
        ]

        def nullHostnameVerifier = [
                verify: { hostname, session -> true }
        ]
        def http = new HTTPBuilder(host)
        SSLContext sc = SSLContext.getInstance("SSL")
        sc.init(null, [nullTrustManager as X509TrustManager] as TrustManager[], null)
        SSLSocketFactory socketFactory = new SSLSocketFactory(sc);
        socketFactory.setHostnameVerifier(nullHostnameVerifier as X509HostnameVerifier)
        http.client.connectionManager.schemeRegistry.register(
                new Scheme("https", socketFactory, 443))
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
}
