import org.apache.tools.ant.taskdefs.condition.Http

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CookieService {

    /**
     * 获取cookie
     * @param name
     * @param request
     * @return
     */
    String getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies()//这样便可以获取一个cookie数组
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue()
            }
        }
        return ""
    }

    def setCookie(String name,String value,HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(86400)
        response.addCookie(cookie);
    }
}
