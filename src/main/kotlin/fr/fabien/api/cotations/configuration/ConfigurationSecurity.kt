package fr.fabien.api.cotations.configuration

import fr.fabien.api.cotations.service.ServiceJWT
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class ConfigurationSecurity(
    private val serviceJWT: ServiceJWT
) {
    @Bean
    @Profile("test")
    fun filterChainTest(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize.anyRequest().permitAll()
            }
        return http.build()
    }

    @Bean
    @Profile("!test")
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/bourse/authentification").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.decoder(
                        NimbusJwtDecoder
                            .withSecretKey(serviceJWT.secretKey)
                            .macAlgorithm(MacAlgorithm.from(serviceJWT.algorithm.name))
                            .build())
                    jwt.jwtAuthenticationConverter(ConverterImpl())
                }
            }
        return http.build()
    }
}
