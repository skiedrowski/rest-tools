package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationNotRequired
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.sameInstance
import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import java.lang.reflect.Method
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.FeatureContext

class AuthenticationFeatureTest {
    private val authenticationProvider = mock<HTTPBasicAuthenticationProvider>()
    private val feature = AuthenticationFeature(authenticationProvider)

    @Test
    fun `configure registers authentication filter`() {

        val resourceInfo = mock<ResourceInfo> {
            val resourceMethod = mock<Method> {
                on { this.isAnnotationPresent(AuthenticationNotRequired::class.java) } doReturn false
            }
            on { this.resourceMethod } doReturn resourceMethod
        }
        val context = mock<FeatureContext>()

        feature.configure(resourceInfo, context)

        argumentCaptor<AuthenticationFilter>().apply {
            verify(context).register(capture())
            assertThat(firstValue.authenticationProvider, sameInstance(authenticationProvider))
        }
    }

    @Test
    fun `configure does not register authentication filter on methods with @AuthenticationNotRequired`() {
        val resourceInfo = mock<ResourceInfo> {
            val resourceMethod = mock<Method> {
                on { this.isAnnotationPresent(AuthenticationNotRequired::class.java) } doReturn true
            }
            on { this.resourceMethod } doReturn resourceMethod
        }
        val context = mock<FeatureContext>()

        feature.configure(resourceInfo, context)

        verify(context, never()).register(any<AuthenticationFilter>())
    }
}