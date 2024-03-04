package ru.prodcontest.services

import ru.prodcontest.db.entities.Token

interface TokenService {
    fun save(token: Token)

    fun exists(token: String): Boolean
}