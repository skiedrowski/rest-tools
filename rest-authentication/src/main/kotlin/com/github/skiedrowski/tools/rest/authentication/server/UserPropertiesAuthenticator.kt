package com.github.skiedrowski.tools.rest.authentication.server

import com.github.skiedrowski.tools.cdiproperties.PropertiesFromFile
import java.util.*
import javax.inject.Inject

class UserPropertiesAuthenticator @Inject constructor(
        @param:PropertiesFromFile("users.properties") private val users: Properties) : Authenticator {
    override fun authenticate(user: String, password: String): Boolean {
        //TODO use a password hash!!!
        return users[user] == password
    }
}