package com.github.skiedrowski.tools.rest.authentication.server

interface Authenticator {
    fun authenticate(user: String, password: String): Boolean
}