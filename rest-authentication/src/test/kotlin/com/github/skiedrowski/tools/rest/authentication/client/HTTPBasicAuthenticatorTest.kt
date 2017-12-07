package com.github.skiedrowski.tools.rest.authentication.client

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.core.MultivaluedMap

class HTTPBasicAuthenticatorTest {
    @Test
    fun `filter adds sensible Authorization header`() {
        val authenticator = HTTPBasicAuthenticator("peter", "petersPassword")

        val headers = mock<MultivaluedMap<String, Any>>()
        val requestContext = mock<ClientRequestContext> {
            on {this.headers} doReturn headers
        }
        authenticator.filter(requestContext)
        
        verify(headers).add("Authorization", "Basic cGV0ZXI6cGV0ZXJzUGFzc3dvcmQ=")
    }
}