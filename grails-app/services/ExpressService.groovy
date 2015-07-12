class ExpressService {

    def serviceMethod() {

    }

    def query(String no,String company){
        if (no&&company){
            def url = "http://www.kuaidi100.com/query?type="+company+"&postid="+no.trim()+"&id=1&valicode=&temp="+new Date().getTime()
            return new URL(url).getText()
        }else{
            return ""
        }

    }
}
