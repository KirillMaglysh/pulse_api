package ru.prodcontest.services

interface AuthenticationService {
    fun createToken(login: String): String

    fun getTokenLogin(token: String): String

    fun parseToken(token: String):String
}