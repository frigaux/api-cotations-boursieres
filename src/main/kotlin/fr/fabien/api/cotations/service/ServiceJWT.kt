package fr.fabien.api.cotations.service

import fr.fabien.api.cotations.restcontroller.dto.DtoAuthentification
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class ServiceJWT(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration.seconds}") private val jwtExpirationInSeconds: Long
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))

    fun generateJwtToken(dtoAuthentification: DtoAuthentification): String {
//        val expirationDate: Date = LocalDateTime.now()
//            .plusSeconds(jwtExpirationInSeconds)
//            .atZone(ZoneId.systemDefault())
//            .toInstant()
//            .let { Date.from(it) }
        return Jwts.builder()
            .subject(dtoAuthentification.identifiant)
            .issuedAt(Date())
            .expiration(Date((Date()).time + jwtExpirationInSeconds))
            .signWith(secretKey)
            .compact()
    }
}