/**
 *
 * 内容选择器
 *
 */
class SpiderSiteSelectorConfig {

    static constraints = {
        sort(inList: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15])
        sort(unique: ['site'])
    }
    Integer sort//第几层
    String selector
    String selectorType //id/class/name/ user defined [selectorType='selector']
    SpiderSite site
}
