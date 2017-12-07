package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.cdiproperties.PropertiesFromFile
import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import com.github.skiedrowski.tools.rest.authentication.HTTPBasic
import java.util.*
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestContext
import javax.xml.bind.DatatypeConverter

@HTTPBasic
class HTTPBasicAuthenticationProvider @Inject constructor(
        @param:PropertiesFromFile("users.properties") private val users: Properties) : AuthenticationProvider {

    override fun authenticateUser(requestContext: ContainerRequestContext): AuthenticatedUserInfo {
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

        //TODO use a password hash!!!
        val valid = users[user] == password

        if (valid) {
            return AuthenticatedUserInfo(user)
        } else {
            throw AuthenticationException("invalid credentials")
        }
    }
}

