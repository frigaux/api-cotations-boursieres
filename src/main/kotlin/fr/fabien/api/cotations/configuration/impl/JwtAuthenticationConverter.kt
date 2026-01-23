package fr.fabien.api.cotations.configuration.impl

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter

/**
 * @see org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
 */
class JwtAuthenticationConverter : Converter<Jwt, AbstractAuthenticationToken> {
    private val jwtGrantedAuthoritiesConverter: Converter<Jwt, Collection<GrantedAuthority>> =
        JwtGrantedAuthoritiesConverter()
            .apply {
                setAuthoritiesClaimName("role")
                setAuthorityPrefix("ROLE_")
            }

    override fun convert(jwt: Jwt): AbstractAuthenticationToken {
        return jwtGrantedAuthoritiesConverter.convert(jwt)
            .let { JwtAuthenticationToken(jwt, it) }
    }
}