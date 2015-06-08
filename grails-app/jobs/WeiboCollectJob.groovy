class WeiboCollectJob {
    static  triggers = {
        cron(name: 'weiboCollect',cronExpression: '0 0/10 * * * ?')
    }

    def execute(){
        def user = WeiboUser.findByUid("5625623786")
        if (user){
            if (user.overdueTime<new Date()) log.error "授权过期！"
        }else {
            log.error "无accesstoken用户！！！！！"
        }
    }
}
