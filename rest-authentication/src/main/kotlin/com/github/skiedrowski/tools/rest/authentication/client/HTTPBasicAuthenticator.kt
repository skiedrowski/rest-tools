package com.github.skiedrowski.tools.rest.authentication.client

import jakarta.annotation.Priority
import jakarta.ws.rs.Priorities
import jakarta.ws.rs.client.ClientRequestContext
import jakarta.xml.bind.DatatypeConverter

@Priority(Priorities.AUTHENTICATION)
class HTTPBasicAuthenticator(private val user: String, private val password: String) : Authenticator {

    private fun getBasicAuthentication(): String {
        val token = "$user:$password"
        return "Basic " + DatatypeConverter.printBase64Binary(token.toByteArray())
    }

    override fun filter(requestContext: ClientRequestContext) {
        requestContext.headers.add("Authorization", getBasicAuthentication())
    }
}
