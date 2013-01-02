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
            createAdminUser(roleAdmin)
        }
    }

    private static void createAdminUser(Role roleAdmin) {
        if (UserRole.findAllByRole(roleAdmin).isEmpty()) {
            def admin = new User(email: 'pvardanega@xebia.fr', password: 'password',
                    username: 'Pierre-Jean Vardanega', oauthId: "102834131238641115022",
                    enabled: true).save(flush: true)
            UserRole.create(admin, roleAdmin, true)
        }
    }

    def destroy = {
    }
}
