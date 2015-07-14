import com.alibaba.fastjson.JSONObject
import org.codehaus.groovy.grails.commons.ApplicationHolder

class ExpressService {
    def serviceMethod() {

    }

    def query(String no,String company){
        return '{"message":"ok","nu":"200093247451","ischeck":"1","com":"yuantong","status":"200","condition":"F00","state":"3","data":[{"time":"2015-04-15 09:09:56","context":"客户 签收人 : 张 已签收","ftime":"2015-04-15 09:09:56"},{"time":"2015-04-15 09:03:00","context":"山东省潍坊市潍城区十笏园分部公司派件人 : 邹辉 派件中 派件员电话","ftime":"2015-04-15 09:03:00"},{"time":"2015-04-14 09:09:16","context":"山东省潍坊市潍城区十笏园分部公司 失败签收录入 于明鑫","ftime":"2015-04-14 09:09:16"},{"time":"2015-04-14 09:03:59","context":"山东省潍坊市潍城区十笏园分部公司派件人 : 邹辉 派件中 派件员电话","ftime":"2015-04-14 09:03:59"},{"time":"2015-04-14 07:15:57","context":"快件到达 山东省潍坊市潍城区十笏园分部公司","ftime":"2015-04-14 07:15:57"},{"time":"2015-04-14 05:50:25","context":"山东省潍坊市公司 已发出,下一站 山东省潍坊市潍城区十笏园分部","ftime":"2015-04-14 05:50:25"},{"time":"2015-04-14 04:07:09","context":"潍坊转运中心公司 已发出,下一站 山东省潍坊市","ftime":"2015-04-14 04:07:09"},{"time":"2015-04-11 19:30:06","context":"浙江省金华市义乌市凌云公司 已打包,发往下一站 潍坊转运中心","ftime":"2015-04-11 19:30:06"},{"time":"2015-04-11 17:58:41","context":"浙江省金华市义乌市凌云公司 已揽收","ftime":"2015-04-11 17:58:41"}]}'
        if (no&&company){
            def url = "http://www.kuaidi100.com/query?type="+company+"&postid="+no.trim()+"&id=1&valicode=&temp="+new Date().getTime()
            return new URL(url).getText()
        }else{
            return ""
        }
    }

}
