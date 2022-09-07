package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationNotRequired
import com.github.skiedrowski.tools.test.rxMockk
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.*
import org.junit.jupiter.api.Test
import java.lang.reflect.Method
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.FeatureContext

class AuthenticationFeatureTest {
    private val authenticationProvider = mockk<HTTPBasicAuthenticationProvider>()
    private val feature = AuthenticationFeature(authenticationProvider)

    @Test
    fun `configure registers authentication filter`() {
        val resourceInfo = rxMockk<ResourceInfo>()
        val resourceMethod = rxMockk<Method> {
            every { isAnnotationPresent(AuthenticationNotRequired::class.java) } returns false
        }
        every { resourceInfo.resourceMethod } returns resourceMethod
        val context = rxMockk<FeatureContext>()


        val authenticationFilterSlot = slot<AuthenticationFilter>()
        every { context.register(capture(authenticationFilterSlot)) } returns rxMockk()

        feature.configure(resourceInfo, context)

        authenticationFilterSlot.captured.authenticationProvider shouldBeSameInstanceAs authenticationProvider
    }

    @Test
    fun `configure does not register authentication filter on methods with @AuthenticationNotRequired`() {
        val resourceInfo = rxMockk<ResourceInfo>()
        val resourceMethod = rxMockk<Method> {
            every { isAnnotationPresent(AuthenticationNotRequired::class.java) } returns true
        }
        every { resourceInfo.resourceMethod } returns resourceMethod
        val context = rxMockk<FeatureContext>()

        feature.configure(resourceInfo, context)

        verify(exactly = 0) { context.register(any<AuthenticationFilter>()) }
    }
}