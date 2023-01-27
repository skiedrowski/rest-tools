package com.github.skiedrowski.tools.rest.authentication.server

import jakarta.annotation.Priority
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerRequestFilter

@Priority(Priorities.AUTHENTICATION)
class AuthenticationFilter(internal val authenticationProvider: HTTPBasicAuthenticationProvider) : ContainerRequestFilter {

    override fun filter(requestContext: ContainerRequestContext) {
        val authenticatedUserInfo = authenticationProvider.authenticateUser(requestContext)
        requestContext.setProperty("authenticatedUserInfo", authenticatedUserInfo)
    }
}