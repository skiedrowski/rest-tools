package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import javax.ws.rs.core.Response

class AuthenticationExceptionMapperTest {
    @Test
    fun toResponse() {
        val ex = mock<AuthenticationException> {
            on { message } doReturn "message"
        }
        val response = AuthenticationExceptionMapper().toResponse(ex)

        assertThat(response.status, equalTo(Response.Status.UNAUTHORIZED.statusCode))
        assertThat(response.getHeaderString("cause"), equalTo("Authorization error: message"))
        assertThat(response.getHeaderString("WWW-Authenticate"), equalTo("Basic realm=\"???\""))
    }
}