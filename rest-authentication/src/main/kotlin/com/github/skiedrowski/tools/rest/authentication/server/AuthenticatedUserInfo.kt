package com.github.skiedrowski.tools.rest.authentication.server

/**
 * After successful authentication, this is stored in the request header "authenticatedUserInfo"
 */
data class AuthenticatedUserInfo(val user: String)