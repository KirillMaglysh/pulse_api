package ru.prodcontest.services

import java.util.*

interface JwsService {
    fun generateToken(login: String): String

    fun isTokenExpired(token: String): Boolean

    fun isTokenExpired(token: String, lastPasswordUpdate: Long): Boolean
    fun extractLogin(token:String): String
}