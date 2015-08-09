/**
 * 百度贴吧帖子内容抓取计划
 */
class TiebaCardSchedule {

    static constraints = {
        exeDate(nullable: true)
    }
    Long start
    Long end
    Date exeDate
}
