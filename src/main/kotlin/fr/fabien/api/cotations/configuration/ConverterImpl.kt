package fr.fabien.api.cotations.configuration

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt

class ConverterImpl : Converter<Jwt, AnonymousAuthenticationToken> {
    override fun convert(jwt: Jwt): AnonymousAuthenticationToken {
        TODO("Not yet implemented")
    }
}