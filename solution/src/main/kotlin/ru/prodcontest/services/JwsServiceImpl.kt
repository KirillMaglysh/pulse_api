package ru.prodcontest.services


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

const val EXPIRATION_TIME = 1000 * 60 * 60 * 6

@Service
class JwtServiceImpl : JwsService {
    @Value("\${token.signing.key}")
    private val jwtSigningKey: String? = null
    private val signingKey: Key
        get() {
            val keyBytes: ByteArray = Decoders.BASE64.decode(jwtSigningKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }

    override fun extractLogin(token: String): String {
        return extractClaim<String>(token, Claims::getSubject)
    }

    override fun generateToken(login: String): String {
        return Jwts.builder().setSubject(login)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, signingKey)
            .compact()
    }

    override fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    override fun isTokenExpired(token: String, lastPasswordUpdate: Long): Boolean {
        return extractExpiration(token).before(Date(lastPasswordUpdate + EXPIRATION_TIME))
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim<Date>(token, Claims::getExpiration)
    }

    private fun <T> extractClaim(token: String, claimsResolvers: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolvers.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(signingKey).build().parseClaimsJws(token)
            .getBody()
    }
}

