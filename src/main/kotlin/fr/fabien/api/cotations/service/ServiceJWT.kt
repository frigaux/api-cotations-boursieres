package fr.fabien.api.cotations.service

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import fr.fabien.api.cotations.restcontroller.dto.DtoAuthentification
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
class ServiceJWT(
    @Value("\${security.jwt.key}") private val jwtKey: String,
    @Value("\${security.jwt.algorithm}") private val jwtAlgorithm: String,
    @Value("\${security.jwt.expiresIn.milliseconds}") private val jwtExpiresInMS: Duration
) {
    final val algorithm: JWSAlgorithm = JWSAlgorithm.parse(jwtAlgorithm)
    final val secretKey: SecretKey = OctetSequenceKey.Builder(jwtKey.toByteArray())
        .algorithm(algorithm)
        .build()
        .toSecretKey()
    private val header: JWSHeader = JWSHeader(algorithm)
    private val signer: MACSigner = MACSigner(secretKey)

    private fun buildClaimsSet(dtoAuthentification: DtoAuthentification): JWTClaimsSet {
        val issuedAt: Instant = Instant.now()
        val expirationTime: Instant = issuedAt.plus(jwtExpiresInMS)

        return JWTClaimsSet.Builder()
            .issueTime(Date.from(issuedAt))
            .expirationTime(Date.from(expirationTime))
            .claim("identifiant", dtoAuthentification.identifiant)
            .build()
    }

    fun generateJwtToken(dtoAuthentification: DtoAuthentification): String {
        return buildClaimsSet(dtoAuthentification)
            .let { SignedJWT(header, it) }
            .also { it.sign(signer) }
            .let { it.serialize() }
    }
}