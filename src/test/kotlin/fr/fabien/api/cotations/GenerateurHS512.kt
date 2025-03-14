package fr.fabien.api.cotations

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator
import java.util.*


class GenerateurHS512 {}

fun main() {
    val jwk = OctetSequenceKeyGenerator(512).keyID(UUID.randomUUID().toString()) // give the key some ID (optional)
        .algorithm(JWSAlgorithm.HS512) // indicate the intended key alg (optional)
        .issueTime(Date()) // issued-at timestamp (optional)
        .generate()
    println(jwk)
}