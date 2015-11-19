import grails.validation.Validateable

/**
 * 将微博中的粉丝关系组成树结构
 */
@Validateable
class WeiboTree {
    /**
     * 关系信息
     */
    Integer ownIdx//自身序号
    Integer parentIdx//父级节点序号
    List<Integer> childNodIds//子节点的ID数组
    Integer deep//层级

    /**
     * 微博信息
     */
    String nickName
//    String

}
