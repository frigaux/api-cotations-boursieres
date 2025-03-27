package fr.fabien.api.cotations.configuration

import fr.fabien.api.cotations.configuration.impl.JwtAuthenticationConverter
import fr.fabien.api.cotations.configuration.impl.YamlPropertySourceFactory
import fr.fabien.api.cotations.service.ServiceJWT
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@OpenAPIDefinition(
    info = Info(
        title = "API bourse",
        version = "\${api.server.version}",
        contact = Contact(
            name = "Fabien Rigaux", email = "Fabien.Rigaux@free.fr"
        ),
        license = License(
            name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
        ),
        description = "Valeurs et cours"
    ),
    servers = [Server(
        url = "\${api.server.base-url}\${server.servlet.context-path}",
        description = "\${api.server.description}"
    )]
)
@PropertySource(value = ["classpath:i18n.yml"], factory = YamlPropertySourceFactory::class)
class ConfigurationSecurity(
    private val serviceJWT: ServiceJWT,
    @Value("\${security.allowed.origins}") private val origins: List<String>
) {

    companion object {
        const val SECURITY_SCHEME_NAME: String = "JWT Bearer Authentication"
    }

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
            .cors { cors ->
                cors.configurationSource(
                    UrlBasedCorsConfigurationSource()
                        .apply {
                            registerCorsConfiguration(
                                "/**",
                                CorsConfiguration().apply {
                                    allowedOrigins = origins
                                    allowedMethods = listOf("GET", "POST")
                                    allowedHeaders = listOf("content-type", "accept")
                                })
                        })
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/bourse/authentification").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.decoder(
                        NimbusJwtDecoder
                            .withSecretKey(serviceJWT.secretKey)
                            .macAlgorithm(MacAlgorithm.from(serviceJWT.algorithm.name))
                            .build()
                    )
                    jwt.jwtAuthenticationConverter(JwtAuthenticationConverter())
                }
            }
        return http.build()
    }

    @Bean
    fun customizeOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(
                Components()
                    .addSecuritySchemes(
                        SECURITY_SCHEME_NAME, SecurityScheme()
                            .name(SECURITY_SCHEME_NAME)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}
