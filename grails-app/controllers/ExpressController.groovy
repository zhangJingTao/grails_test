
class ExpressController {
def sendMailService

    def index() {
        sendMailService.SendEmailAsynchronously("610039879@qq.com","fdjslkatest","fklslj21body!!!")
        sendMailService.sendMail()
    }
}
