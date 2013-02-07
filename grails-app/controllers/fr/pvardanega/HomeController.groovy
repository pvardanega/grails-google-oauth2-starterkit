package fr.pvardanega

import org.springframework.security.access.annotation.Secured

@Secured("IS_AUTHENTICATED_FULLY")
class HomeController {

    def index() {
        render "Hello world !"
    }
}
