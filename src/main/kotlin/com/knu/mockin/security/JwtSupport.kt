package com.knu.mockin.security

import com.knu.mockin.util.RedisUtil
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class BearerToken(val value: String) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
    override fun getCredentials(): Any = value
    override fun getPrincipal(): Any = value

}

@Component
class JwtSupport {

    private val key = Keys.hmacShaKeyFor("HzA9XwnQLwwPt2Qjn06LirX3zXCYsKXqOuNXm63Ex9o=".toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()

    fun generate(username: String): BearerToken {
        val builder = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
            .signWith(key)

        val token = builder.compact()
        RedisUtil.saveToken(username, token)

        return BearerToken(token)

    }

    fun getUsername(token: BearerToken): String {
        return parser.parseClaimsJws(token.value).body.subject
    }

    fun isValid(token: BearerToken, user: UserDetails?): Boolean {
        val claims = parser.parseClaimsJws(token.value).body
        val unexpired = claims.expiration.after(Date.from(Instant.now()))

        val storedToken = RedisUtil.getToken(claims.subject)

        return unexpired && (claims.subject == user?.username) && (token.value == storedToken)
    }


}