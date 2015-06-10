import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.PropertiesLoaderUtils

/**
 * 微博原图本地化
 */
class WeiboImgDownloadJob {
    static  triggers = {
        cron(name: 'weiboCollect',cronExpression: '50 0/10 * * * ?')
    }

    def properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource('app.properties'))
    def imgFolder = properties.getProperty("imgFolder")

    def execute(){
        def list = WeiboCollectImg.findAllByDownloadedAndNeedDownload(false,true).asList()
        list.each {
            img ->
                def imgUrl = img.imgUrl
                def fileName = imgUrl.substring(imgUrl.lastIndexOf("/"),imgUrl.length())
                println "fileName:"+fileName
                def location =imgFolder+"/"+img.collect.commentUserId+"/"
                if (!new File(location).exists()){
                    new File(location).mkdirs()
                }
                img.downloadDate = new Date()
                img.downloaded = true
                img.needDownload = false
                img.diskPath = location + fileName
                img.save(flush: true)
                def cmd = "wget -P "+location+" "+imgUrl
                println cmd
                cmd.execute().text
        }
    }
}
