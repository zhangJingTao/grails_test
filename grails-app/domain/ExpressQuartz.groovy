/**
 * 待查快递
 */
class ExpressQuartz {

    static constraints = {
        notiEmail(email: true)
    }


    String expressNo
    String company
    Date lastUpdate
    String lastQueryJson
    String lastNu
    Boolean unChecked
    String notiEmail


}
