class Permission {
    String permission
    String name
    static hasMany = [roles:Role]
    static constraints = {
    }
}