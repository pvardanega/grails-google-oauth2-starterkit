package fr.pvardanega.user

class AuthController {

    SpringSecuritySigninService springSecuritySigninService

    def signin = {
        GrailsOAuthService service = resolveService(params.provider)
        if (!service) {
            redirect(url: '/')
        }

        session["${params.provider}_originalUrl"] = params.originalUrl

        def callbackParams = [provider: params.provider]
        def callback = "${createLink(action: 'callback', absolute: 'true', params: callbackParams)}"
        def authInfo = service.getAuthInfo(callback)

        session["${params.provider}_authInfo"] = authInfo

        redirect(url: authInfo.authUrl)
    }

    def callback = {
        GrailsOAuthService service = resolveService(params.provider)
        if (!service) {
            redirect(url: '/')
        }

        AuthInfo authInfo = session["${params.provider}_authInfo"]

        def requestToken = authInfo.requestToken
        def accessToken = service.getAccessToken(authInfo.service, params, requestToken)
        session["${params.provider}_authToken"] = accessToken
        def profile = service.getProfile(authInfo.service, accessToken)
        session["${params.provider}_profile"] = profile

        def uid = profile.uid
        User user = User.findByOauthId(uid)

        if (!user) {
            user = new User(email: profile.email, username: profile.username, oauthId: uid, enabled: true,
                    password: "password")
            if (!user.save(flush: true)) {
                flash.message = message(code: "auth.error.user.creation")
                redirect controller: "login", action: "auth"
                return
            }
            UserRole.create user, Role.findByAuthority(Role.ROLE_USER), true
        }

        springSecuritySigninService.signIn(user)
        redirect(uri: (session["${params.provider}_originalUrl"] ?: '/') - request.contextPath)
    }

    private def resolveService(provider) {
        def serviceName = "${ provider as String }AuthService"
        grailsApplication.mainContext.getBean(serviceName)
    }

}