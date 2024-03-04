package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.Token
import ru.prodcontest.db.reposetories.TokenRepository

@Service
class TokenServiceImpl(
    @Autowired private val tokenRepo: TokenRepository
) : TokenService {
    override fun save(token: Token) {
        tokenRepo.save(token)
    }

    override fun exists(token: String): Boolean {
        return tokenRepo.existsById(token)
    }

}