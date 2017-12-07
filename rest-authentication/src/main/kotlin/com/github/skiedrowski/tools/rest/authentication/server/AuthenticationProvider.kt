package com.github.skiedrowski.tools.rest.authentication.server

import javax.ws.rs.container.ContainerRequestContext

interface AuthenticationProvider {
    fun authenticateUser(requestContext: ContainerRequestContext): AuthenticatedUserInfo
}