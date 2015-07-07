/**
 * 快递自动更新任务
 */
class ExpressJob {
    def sendMailService

    static  triggers = {
        cron(name: 'expressJob',cronExpression: '0 0/30 * * * ?')
    }
    def execute = {

    }
}
