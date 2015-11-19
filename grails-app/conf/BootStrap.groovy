class BootStrap {
    def shiroSecurityService

    def init = { servletContext ->
        def adminRole = Role.findByName('ROLE_ADMIN') ?:
                new Role(name: 'ROLE_ADMIN').save(flush: true, failOnError: true)

        // Create the user role
        def userRole = Role.findByName('ROLE_USER') ?:
                new Role(name: 'ROLE_USER').save(flush: true, failOnError: true)

        // Create an admin user
        def adminUser = User.findByUsername('admin') ?:
                new User(username: "admin",
                        passwordHash: shiroSecurityService.encodePassword('password'))
                        .save(flush: true, failOnError: true)

        // Add roles to the admin user
        assert adminUser.addToRoles(adminRole)
                .addToRoles(userRole)
                .save(flush: true, failOnError: true)

        // Create an standard user
        def standardUser = User.findByUsername('joe') ?:
                new User(username: "joe",
                        passwordHash: shiroSecurityService.encodePassword('password'))
                        .save(flush: true, failOnError: true)

        // Add role to the standard user
        assert standardUser.addToRoles(userRole)
                .save(flush: true, failOnError: true)

        //增加一个 permission 限制 controller - Home 下 action -admin ID为2 的权限
        def per1 = new Permission(permission:"home:admin:2",name: "测试").save(flush: true, failOnError: true)
        def zhihuCollect = new Permission(permission:"zhihuCollect",name: "知乎配置").save(flush: true, failOnError: true)
        adminRole.addToPermissions("zhihuCollect").save(flush: true, failOnError: true)

    }
    def destroy = {
    }
}
