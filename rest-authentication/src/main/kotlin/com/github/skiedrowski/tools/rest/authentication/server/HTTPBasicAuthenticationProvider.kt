package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import javax.enterprise.inject.Instance
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.xml.bind.DatatypeConverter

class HTTPBasicAuthenticationProvider @Inject constructor(
        private val authenticator: Instance<Authenticator>) {

    fun authenticateUser(requestContext: ContainerRequestContext): AuthenticatedUserInfo {
        val authorizationHeader = requestContext.getHeaderString("Authorization")

        authorizationHeader ?: throw AuthenticationException("no authorization header")

        if (!authorizationHeader.startsWith("Basic ", ignoreCase = true)) {
            throw AuthenticationException("only 'Basic' authentication supported")
        }

        val authInfo = authorizationHeader.substringAfter(" ")
        val authInfoBytes = DatatypeConverter.parseBase64Binary(authInfo)
        if (authInfoBytes == null || authInfoBytes.isEmpty()) {
            throw AuthenticationException("no credentials")
        }
        val decodedAuth = String(authInfoBytes)
        if (!decodedAuth.contains(':')) {
            throw AuthenticationException("invalid credentials format")
        }
        val (user, password) = decodedAuth.split(":")

        val valid = authenticator.get().authenticate(user, password)

        if (valid) {
            return AuthenticatedUserInfo(user)
        } else {
            throw AuthenticationException("invalid credentials")
        }
    }
}

