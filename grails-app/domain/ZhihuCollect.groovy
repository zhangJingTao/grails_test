/**
 * 知乎
 */
class ZhihuCollect {

    static constraints = {
        collectId(unique: true)
    }
    String collectId
    Boolean enabled
    Date dateCreated
}
