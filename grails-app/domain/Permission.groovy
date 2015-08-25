class Permission {
    String permission
    static hasMany = [roles:Role]
    static constraints = {
    }
}