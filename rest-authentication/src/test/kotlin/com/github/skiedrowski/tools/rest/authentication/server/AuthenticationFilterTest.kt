package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.test.rxMockk
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import jakarta.ws.rs.container.ContainerRequestContext

class AuthenticationFilterTest {

    @Test
    fun `filter addsAuthenticatedUserInfo`() {
        val requestContext = rxMockk<ContainerRequestContext>()
        val authenticatedUserInfo = rxMockk<AuthenticatedUserInfo>()

        val authenticationProvider = rxMockk<HTTPBasicAuthenticationProvider>() {
            every { authenticateUser(requestContext) } returns authenticatedUserInfo
        }

        val authenticationFilter = AuthenticationFilter(authenticationProvider)
        authenticationFilter.filter(requestContext)

        verify { requestContext.setProperty("authenticatedUserInfo", authenticatedUserInfo) }
    }

}