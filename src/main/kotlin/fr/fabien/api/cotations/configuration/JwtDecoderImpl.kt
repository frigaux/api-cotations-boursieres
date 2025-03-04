package fr.fabien.api.cotations.configuration

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder

// TODO : delete
class JwtDecoderImpl: JwtDecoder {
    override fun decode(token: String): Jwt {
        TODO("Not yet implemented")
    }
}