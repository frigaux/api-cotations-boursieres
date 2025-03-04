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
    @Value("\${jwt.key}") private val jwtKey: String,
    @Value("\${jwt.algorithm}") private val jwtAlgorithm: String,
    @Value("\${jwt.expiresIn.seconds}") private val jwtExpiresInSeconds: Duration
) {

    private val algorithm: JWSAlgorithm = JWSAlgorithm.parse(jwtAlgorithm)
    private val secretKey: SecretKey = OctetSequenceKey.Builder(jwtKey.toByteArray())
        .algorithm(JWSAlgorithm.parse(jwtAlgorithm))
        .build()
        .toSecretKey()
    private val header: JWSHeader = JWSHeader(algorithm)
    private val signer: MACSigner = MACSigner(secretKey)


    private fun buildClaimsSet(dtoAuthentification: DtoAuthentification): JWTClaimsSet {
        val issuedAt = Instant.now()
        val expirationTime = issuedAt.plus(jwtExpiresInSeconds)

        val builder = JWTClaimsSet.Builder()
            .issueTime(Date.from(issuedAt))
            .expirationTime(Date.from(expirationTime))

        builder.claim("identifiant", dtoAuthentification.identifiant)

        return builder.build()
    }


    fun generateJwtToken(dtoAuthentification: DtoAuthentification): String {
        val claimsSet = buildClaimsSet(dtoAuthentification)
        val jwt = SignedJWT(header, claimsSet)
        jwt.sign(signer)
        return jwt.serialize()
    }
}