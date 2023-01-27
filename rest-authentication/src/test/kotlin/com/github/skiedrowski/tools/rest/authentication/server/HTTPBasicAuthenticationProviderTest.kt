package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import com.github.skiedrowski.tools.test.rxMockk
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import jakarta.enterprise.inject.Instance
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.xml.bind.DatatypeConverter

class HTTPBasicAuthenticationProviderTest {

    private val authenticatorInstance = mockk<Instance<Authenticator>> {
        val authenticator = mockk<Authenticator> {
            every { authenticate("Peter", "") } returns false
            every { authenticate("", "password") } returns false
            every { authenticate("peter", "petersPassword") } returns true
            every { authenticate("peteu�]r", "petersPassword") } returns false
        }
        every { get() } returns authenticator
    }
    private val authenticationProvider = HTTPBasicAuthenticationProvider(authenticatorInstance)

    @Test
    fun `valid peter`() {
        val requestContext = rxMockk<ContainerRequestContext> {
            every { getHeaderString("Authorization") } returns "Basic cGV0ZXI6cGV0ZXJzUGFzc3dvcmQ="
        }
        val authenticatedUserInfo = authenticationProvider.authenticateUser(requestContext)

        authenticatedUserInfo.user shouldBe "peter"
    }

    @Test
    fun `wrong password for peter`() {
        val requestContext = rxMockk<ContainerRequestContext> {
            every { getHeaderString("Authorization") } returns "Basic cGV0ZXXXXXI6cGV0ZXJzUGFzc3dvcmQ="
        }
        val authenticationException = assertThrows<AuthenticationException> {
            authenticationProvider.authenticateUser(requestContext)
        }
        authenticationException.message shouldBe "invalid credentials"
    }

    @Test
    fun `no basic authorization`() {
        val requestContext = rxMockk<ContainerRequestContext> {
            every { getHeaderString("Authorization") } returns "DIGEST cGV0ZXXXXXI6cGV0ZXJzUGFzc3dvcmQ="
        }
        val authenticationException = assertThrows<AuthenticationException> { authenticationProvider.authenticateUser(requestContext) }
        authenticationException.message shouldBe "only 'Basic' authentication supported"
    }

    @Test
    fun `no password`() {
        val requestContext = rxMockk<ContainerRequestContext> {
            every { getHeaderString("Authorization") } returns "Basic " + DatatypeConverter.printBase64Binary("Peter:".toByteArray())
        }
        val authenticationException = assertThrows<AuthenticationException> {
            authenticationProvider.authenticateUser(requestContext)
        }
        authenticationException.message shouldBe "invalid credentials"
    }

    @Test
    fun `no username`() {
        val requestContext = rxMockk<ContainerRequestContext> {
            every { getHeaderString("Authorization") } returns "Basic " + DatatypeConverter.printBase64Binary(":password".toByteArray())
        }
        val authenticationException = assertThrows<AuthenticationException> {
            authenticationProvider.authenticateUser(requestContext)
        }
        authenticationException.message shouldBe "invalid credentials"
    }

    @Test
    fun `Basic user without colon`() {
        val requestContext = rxMockk<ContainerRequestContext> {
            every { getHeaderString("Authorization") } returns "Basic " + DatatypeConverter.printBase64Binary("Peter".toByteArray())
        }
        val authenticationException = assertThrows<AuthenticationException> { authenticationProvider.authenticateUser(requestContext) }
        authenticationException.message shouldBe "invalid credentials format"
    }
}