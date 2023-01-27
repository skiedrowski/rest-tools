package com.github.skiedrowski.tools.rest.authentication

import jakarta.ws.rs.NameBinding

/**
 * placing this annotation at a JAX-RS method allows using the method without authentication
 */
@NameBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AuthenticationNotRequired