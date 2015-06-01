import com.alibaba.fastjson.JSONObject
import grails.converters.JSON
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
import groovy.time.TimeCategory

/**
 * weibo授权
 *
 * @see http://open.weibo.com/wiki/Oauth2/authorize
 * url:http://www.onlysleep.net/weibo/accessToken
 */
class WeiboController {
    def properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource('app.properties'))
    def appId = properties.getProperty("appKey")
    def appSecret = properties.getProperty("appSecret")

    def index() {}
    def test = {

    }
    /**
     * Oauth请求
     * https://api.weibo.com/oauth2/authorize
     */
    def oauth = {
        def url = "https://api.weibo.com/oauth2/authorize?client_id=" + appId + "&redirect_uri=http://www.onlysleep.net/weibo/accessToken&scope=all"
        redirect(url: url)
    }
    /**
     * 根据code获取accessToken
     */
    def accessToken = {
        def code = params.code
        if (code) {
            def host = 'https://api.weibo.com'
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
            http.request(Method.POST, JSON) { req ->
                uri.path = '/oauth2/access_token'
                //传递的参数,将要按照你所指定的格式进行发送
                uri.query = [client_id: appId, client_secret: appSecret, grant_type: "authorization_code", code: params.code, redirect_uri: "http://www.onlysleep.net/weibo/accessToken"]
                headers.'User-Agent' = 'Mozilla/5.0'
                println uri.query
                req.getParams().setParameter("http.connection.timeout", new Integer(60000));
                req.getParams().setParameter("http.socket.timeout", new Integer(60000));
                response.success = { resp, json ->
                    String res = IOUtils.toString(json as EofSensorInputStream, "UTF-8")
                    if (res) {
                        JSONObject tokenJson = JSONObject.parseObject(res)
                        log.info tokenJson.toString()
                        def user = WeiboUser.findByUid(tokenJson.getString("uid"))
                        def overduTime = new Date()
                        use(TimeCategory){
                            int second = tokenJson.getIntValue("expires_in")
                            overduTime = overduTime + second.seconds
                        }
                        if (user) {
                            user.accessToken = tokenJson.getString("access_token")
                            user.lastTokenTime = new Date()
                            user.overdueTime = overduTime
                            user.save(flush: true)
                        } else {
                            WeiboUser wu = new WeiboUser(accessToken: tokenJson.getString("access_token"),
                                    lastTokenTime: new Date(),
                                    overdueTime: overduTime,
                                    uid: tokenJson.getString("uid"),
                                    createTime: new Date())
                            wu.save(flush: true)
                        }
                        redirect(uri: "/?source=weibo")
                    }
                }
                response.failure = { resp ->
                    println resp
                    render "访问失败"
                }
            }
        } else {
            log.error "授权失败:" + params.toString()
            redirect(uri: "/")
        }
    }
}
