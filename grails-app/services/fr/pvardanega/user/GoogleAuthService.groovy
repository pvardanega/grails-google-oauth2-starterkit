package fr.pvardanega.user

import grails.converters.JSON
import org.scribe.model.*
import org.scribe.oauth.OAuthService
import org.scribe.builder.api.GoogleApi20

class GoogleAuthService extends GrailsOAuthService {

    @Override
    OAuthService createOAuthService(String callbackUrl) {
        def builder = createServiceBuilder(GoogleApi20,
                grailsApplication.config.auth.google.key as String,
                grailsApplication.config.auth.google.secret as String,
                callbackUrl)
        return builder.grantType(OAuthConstants.AUTHORIZATION_CODE)
                .scope('https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email')
                .build()
    }

    AuthInfo getAuthInfo(String callbackUrl) {
        OAuthService authService = createOAuthService(callbackUrl)
        new AuthInfo(authUrl: authService.getAuthorizationUrl(null), service: authService)
    }

    Token getAccessToken(OAuthService authService, Map params, Token requestToken) {
        Verifier verifier = new Verifier(params.code)
        authService.getAccessToken(requestToken, verifier)
    }

    OAuthProfile getProfile(OAuthService authService, Token accessToken) {

        OAuthRequest request = new OAuthRequest(Verb.GET, 'https://www.googleapis.com/oauth2/v1/userinfo')
        authService.signRequest(accessToken, request)

        def response = request.send()

        def user = JSON.parse(response.body)
        def login = user.given_name.capitalize() + " " + user.family_name.capitalize()
        new OAuthProfile(username: login, email: user.email, uid: user.id)
    }
}