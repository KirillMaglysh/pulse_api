package ru.prodcontest.db.reposetories

import org.springframework.data.jpa.repository.JpaRepository
import ru.prodcontest.db.entities.Token

interface TokenRepository : JpaRepository<Token, String> {
    fun save(token: Token)
}