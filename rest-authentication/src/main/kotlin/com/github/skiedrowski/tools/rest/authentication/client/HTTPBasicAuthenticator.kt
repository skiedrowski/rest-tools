package com.github.skiedrowski.tools.rest.authentication.client

import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.client.ClientRequestContext
import javax.xml.bind.DatatypeConverter

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
