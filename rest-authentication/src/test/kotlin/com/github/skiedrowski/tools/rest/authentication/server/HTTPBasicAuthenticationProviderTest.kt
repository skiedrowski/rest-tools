package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*
import javax.ws.rs.container.ContainerRequestContext
import javax.xml.bind.DatatypeConverter

class HTTPBasicAuthenticationProviderTest {

    @JvmField
    @Rule
    val thrown: ExpectedException = ExpectedException.none()

    private lateinit var authenticationProvider: HTTPBasicAuthenticationProvider

    @Before
    fun context() {
        val properties = Properties()
        properties["peter"] = "petersPassword"
        authenticationProvider = HTTPBasicAuthenticationProvider(properties)
    }

    @Test
    fun `valid peter`() {
        val requestContext = mock<ContainerRequestContext> {
            on { getHeaderString("Authorization") } doReturn "Basic cGV0ZXI6cGV0ZXJzUGFzc3dvcmQ="
        }
        val authenticatedUserInfo = authenticationProvider.authenticateUser(requestContext)

        assertThat(authenticatedUserInfo.user, equalTo("peter"))
    }

    @Test
    fun `wrong password for peter`() {
        val requestContext = mock<ContainerRequestContext> {
            on { getHeaderString("Authorization") } doReturn "Basic cGV0ZXXXXXI6cGV0ZXJzUGFzc3dvcmQ="
        }
        thrown.expect(AuthenticationException::class.java)
        thrown.expectMessage("invalid credentials")
        authenticationProvider.authenticateUser(requestContext)
    }

    @Test
    fun `no basic authorization`() {
        val requestContext = mock<ContainerRequestContext> {
            on { getHeaderString("Authorization") } doReturn "DIGEST cGV0ZXXXXXI6cGV0ZXJzUGFzc3dvcmQ="
        }
        thrown.expect(AuthenticationException::class.java)
        thrown.expectMessage("only 'Basic' authentication supported")
        authenticationProvider.authenticateUser(requestContext)
    }

    @Test
    fun `no password`() {
        val requestContext = mock<ContainerRequestContext> {
            on { getHeaderString("Authorization") } doReturn "Basic " + DatatypeConverter.printBase64Binary("Peter:".toByteArray())
        }
        thrown.expect(AuthenticationException::class.java)
        thrown.expectMessage("invalid credentials")
        authenticationProvider.authenticateUser(requestContext)
    }

    @Test
    fun `no username`() {
        val requestContext = mock<ContainerRequestContext> {
            on { getHeaderString("Authorization") } doReturn "Basic " + DatatypeConverter.printBase64Binary(":password".toByteArray())
        }
        thrown.expect(AuthenticationException::class.java)
        thrown.expectMessage("invalid credentials")
        authenticationProvider.authenticateUser(requestContext)
    }

    @Test
    fun `Basic user without colon`() {
        val requestContext = mock<ContainerRequestContext> {
            on { getHeaderString("Authorization") } doReturn "Basic " + DatatypeConverter.printBase64Binary("Peter".toByteArray())
        }
        thrown.expect(AuthenticationException::class.java)
        thrown.expectMessage("invalid credentials format")
        authenticationProvider.authenticateUser(requestContext)
    }
}