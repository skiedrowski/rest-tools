package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.rest.authentication.AuthenticationNotRequired
import com.github.skiedrowski.tools.rest.authentication.HTTPBasic
import javax.inject.Inject
import javax.ws.rs.container.DynamicFeature
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.FeatureContext
import javax.ws.rs.ext.Provider

@Provider
class AuthenticationFeature @Inject constructor(
        @param:HTTPBasic private val authenticationProvider: AuthenticationProvider) : DynamicFeature {

    override fun configure(resourceInfo: ResourceInfo, context: FeatureContext) {
        val method = resourceInfo.resourceMethod
        if (!method.isAnnotationPresent(AuthenticationNotRequired::class.java)) {
            context.register(AuthenticationFilter(authenticationProvider))
        }
    }
}