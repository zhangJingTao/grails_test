import com.alibaba.fastjson.JSONObject

/**
 * 贴吧自动抓取服务，每半小时执行一次抓取
 */
class TiebaJob {
    static  triggers = {
        cron(name: 'tiebaJob',cronExpression: '0 0/30 * * * ?')
    }
    def execute(){
        List<TiebaCardSchedule> jobs = TiebaCardSchedule.findAllByExeDateIsNull()
        for (int i=0;i<2;i++){
            if (jobs.size()-1>=i){
                def tiebaSchedule = jobs.get(i)
                tiebaSchedule.exeDate = new Date()
                tiebaSchedule.save(flush: true)
                def cmd = "curl http://www.onlysleep.net/tieba/getIt?start=${tiebaSchedule.start}&end=${tiebaSchedule.end}"
                log.info cmd
                cmd.execute()
            }else{
                log.error("计划不足两个 立即补充...")
            }
        }
    }
}
