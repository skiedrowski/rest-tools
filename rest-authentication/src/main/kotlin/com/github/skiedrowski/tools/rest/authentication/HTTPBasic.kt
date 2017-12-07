package com.github.skiedrowski.tools.rest.authentication

import javax.inject.Qualifier

/**
 * Use this qualifier with an [AuthenticationProvider] to indicate that you want to use HTTP Basic authentication.
 */
@Qualifier
@Target(AnnotationTarget.FIELD,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HTTPBasic
