package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationNotRequired
import javax.inject.Inject
import javax.ws.rs.container.DynamicFeature
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.FeatureContext
import javax.ws.rs.ext.Provider

@Provider
class AuthenticationFeature @Inject constructor(
        private val authenticationProvider: HTTPBasicAuthenticationProvider) : DynamicFeature {

    override fun configure(resourceInfo: ResourceInfo, context: FeatureContext) {
        val method = resourceInfo.resourceMethod
        if (!method.isAnnotationPresent(AuthenticationNotRequired::class.java)) {
            context.register(AuthenticationFilter(authenticationProvider))
        }
    }
}