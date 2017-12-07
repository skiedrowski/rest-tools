package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class AuthenticationExceptionMapper : ExceptionMapper<AuthenticationException> {
    override fun toResponse(exception: AuthenticationException): Response {
        return Response.status(Response.Status.UNAUTHORIZED)
                .header("cause", "Authorization error: ${exception.message}")
                //https://en.wikipedia.org/wiki/Basic_access_authentication
                //https://de.wikipedia.org/wiki/HTTP-Authentifizierung
                .header("WWW-Authenticate", "Basic realm=\"???\"")
                .build()
    }
}