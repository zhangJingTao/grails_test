import groovy.text.SimpleTemplateEngine
import groovy.text.Template
import groovy.text.TemplateEngine

/**
 * 这个类可以考虑优化成通用的╮(╯▽╰)╭
 */
class ExpressMailContentGenerator {
    public Writable getContent(def templatePath,String content) {

        TemplateEngine engine = new SimpleTemplateEngine()
        Template template = engine.createTemplate(new File(templatePath))
        //把模型数据归并到模板中，通过 Map 来传递参数
        Writable result = template.make(content: content)
        println result
        return result
    }

    public static void main(String[] args) {
//        ExpressMailContentGenerator generator = new ExpressMailContentGenerator()
//        def content = generator.getContent("C:\\git_work\\sleep-site\\grails-app\\views\\express\\expressMail.ftl","fdsafdsa")
//        SendMailService sms = new SendMailService()
//        sms.SendEmailAsynchronously("610039879@qq.com","测试123",content)
    }


}




