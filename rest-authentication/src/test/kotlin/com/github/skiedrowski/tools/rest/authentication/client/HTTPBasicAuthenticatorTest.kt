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
//        val authenticator = HTTPBasicAuthenticator("peter", "petersPassword")
//
//        val headers = mock<MultivaluedMap<String, Any>>()
//        val requestContext = mock<ClientRequestContext> {
//            on {this.headers} doReturn headers
//        }
//        authenticator.filter(requestContext)
//        
//        verify(headers).add("Authorization", "Basic cGV0ZXI6cGV0ZXJzUGFzc3dvcmQ=")
        checkAuthHeader("peter", "petersPassword", "Basic cGV0ZXI6cGV0ZXJzUGFzc3dvcmQ=")
    }

    @Test
    fun `filter adds sensible Authorization header 2`() {
        checkAuthHeader("mauveS3", "bmx\$2+0-1=1,oder?", "Basic bWF1dmVTMzpibXgkMiswLTE9MSxvZGVyPw==")
        checkAuthHeader("mauveS3", "mauveS3-test", "Basic bWF1dmVTMzptYXV2ZVMzLXRlc3Q=")
    }

    private fun checkAuthHeader(username: String, password: String, expHeaderValue: String) {
        val authenticator = HTTPBasicAuthenticator(username, password)

        val headers = mock<MultivaluedMap<String, Any>>()
        val requestContext = mock<ClientRequestContext> {
            on { this.headers } doReturn headers
        }
        authenticator.filter(requestContext)

        verify(headers).add("Authorization", expHeaderValue)
    }

}