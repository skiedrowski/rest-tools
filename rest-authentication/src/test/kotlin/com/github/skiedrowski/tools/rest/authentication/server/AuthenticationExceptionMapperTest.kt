package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationException
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import jakarta.ws.rs.core.Response

class AuthenticationExceptionMapperTest {
    @Test
    fun toResponse() {
        val ex = mockk<AuthenticationException> {
            every { message } returns "message"
        }
        val response = AuthenticationExceptionMapper("my realm").toResponse(ex)

        response.status shouldBe Response.Status.UNAUTHORIZED.statusCode
        response.getHeaderString("cause") shouldBe "Authorization error: message"
        response.getHeaderString("WWW-Authenticate") shouldBe "Basic realm=\"my realm\""
    }
}