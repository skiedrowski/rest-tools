package com.github.skiedrowski.tools.rest.authentication.server

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import javax.ws.rs.container.ContainerRequestContext

class AuthenticationFilterTest {
    private val authenticationProvider = mock<HTTPBasicAuthenticationProvider>()
    private val authenticationFilter = AuthenticationFilter(authenticationProvider)

    @Test
    fun `filter addsAuthenticatedUserInfo`() {
        val requestContext = mock<ContainerRequestContext>()
        val authenticatedUserInfo = mock<AuthenticatedUserInfo>()
        whenever(authenticationProvider.authenticateUser(requestContext)).thenReturn(authenticatedUserInfo)
        
        authenticationFilter.filter(requestContext)
        
        verify(requestContext).setProperty("authenticatedUserInfo", authenticatedUserInfo)
    }

}