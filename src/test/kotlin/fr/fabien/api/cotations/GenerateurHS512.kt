package fr.fabien.api.cotations

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Encoders
import javax.crypto.SecretKey

class GenerateurHS512 {}

fun main() {
    val key: SecretKey = Jwts.SIG.HS512.key().build()
    println(Encoders.BASE64.encode(key.encoded))
}