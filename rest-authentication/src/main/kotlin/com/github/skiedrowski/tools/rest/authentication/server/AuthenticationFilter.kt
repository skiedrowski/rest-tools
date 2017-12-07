package com.github.skiedrowski.tools.rest.authentication.server

import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter

@Priority(Priorities.AUTHENTICATION)
class AuthenticationFilter(internal val authenticationProvider: HTTPBasicAuthenticationProvider) : ContainerRequestFilter {

    override fun filter(requestContext: ContainerRequestContext) {
        val authenticatedUserInfo = authenticationProvider.authenticateUser(requestContext)
        requestContext.setProperty("authenticatedUserInfo", authenticatedUserInfo)
    }
}