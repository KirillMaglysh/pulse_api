package ru.prodcontest.services

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.Token
import ru.prodcontest.db.exceptions.TokenException

const val TOKEN_PREFIX = "Bearer"

@Service
class AuthenticationServiceImpl(
    @Autowired private val tableCreationService: TableCreationService,
    @Autowired private val tablePasswordUpdatesService: PasswordUpdatesService,
    @Autowired private val tokenService: TokenService,
    @Autowired private val jwsService: JwsService
) : AuthenticationService {

    init {
        tableCreationService.createTokensTableIfNotExists()
        tableCreationService.createPasswordUpdatesTableIfNotExists()
    }

    override fun createToken(login: String): String {
        val token = jwsService.generateToken(login)
        tokenService.save(Token(token))
        return token
    }

    override fun getTokenLogin(token: String): String {
        if (!tokenService.exists(token)) {
            throw TokenException("Invalid token")
        }

        try {
            if (jwsService.isTokenExpired(token)) {
                throw TokenException("Token is expired")
            }

            val login = jwsService.extractLogin(token)
            if (jwsService.isTokenExpired(token, tablePasswordUpdatesService.getLastUpdate(login))) {
                throw TokenException("Token is expired")
            }
        } catch (e: ExpiredJwtException) {
            throw TokenException("Token is expired")
        }

        return jwsService.extractLogin(token)
    }

    override fun parseToken(token: String): String {
        if (!token.startsWith(TOKEN_PREFIX)) {
            throw TokenException("Invalid token")
        }

        return token.removePrefix(TOKEN_PREFIX).trim()
    }
}