package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.cdiproperties.PropertyFromFile
import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class AuthenticationExceptionMapper @Inject constructor(
        @PropertyFromFile(key = "www_authenticate_realm_name") private val realmName: String?)
    : ExceptionMapper<AuthenticationException> {

    override fun toResponse(exception: AuthenticationException): Response {
        return Response.status(Response.Status.UNAUTHORIZED)
                .header("cause", "Authorization error: ${exception.message}")
                //https://en.wikipedia.org/wiki/Basic_access_authentication
                //https://de.wikipedia.org/wiki/HTTP-Authentifizierung
                .header("WWW-Authenticate", "Basic realm=\"$realmName\"")
                .build()
    }
}