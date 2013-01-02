import fr.xebia.xke.user.Role
import fr.xebia.xke.user.User
import fr.xebia.xke.user.UserRole

class BootStrap {

    def init = { servletContext ->

        // Creates application roles
        def roleAdmin = Role.findByAuthority(Role.ROLE_ADMIN) ?: new Role(authority: Role.ROLE_ADMIN).save(true)
        def roleUser = Role.findByAuthority(Role.ROLE_USER) ?: new Role(authority: Role.ROLE_USER).save(true)

        development {
            createAdminUser(roleAdmin)
        }
        test {
            createAdminUser(roleAdmin)
        }
        production {

        }
    }

    private void createAdminUser(Role roleAdmin) {
        if (UserRole.findAllByRole(roleAdmin).isEmpty()) {
            def admin = new User(email: "testxke@yopmail.com", username: "testxke", enabled: true,
                    password: "password").save(true)
            UserRole.create(admin, roleAdmin, true)
        }
    }

    def destroy = {
    }
}
