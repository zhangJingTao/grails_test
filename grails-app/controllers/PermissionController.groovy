class PermissionController {
    def index() {}

    def save() {
        def uri = params.pUrl as String
        def name = params.name as String
        Permission p = new Permission(
                permission: uri.replace("/", ":"),
                name: name
        )
        p.save(flush: true)
        render "ok"
    }

    def addRole = {
        def roleId = params.roleId
        def permission = params.permission
        Role.findById(roleId).addToPermissions(permission)
        render "ok"
    }
}
