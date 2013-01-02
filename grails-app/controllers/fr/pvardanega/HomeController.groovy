package fr.pvardanega

import grails.plugins.springsecurity.Secured

@Secured("IS_AUTHENTICATED_FULLY")
class HomeController {

    def index() {
        render "Hello world !"
    }
}
