package fr.fabien.api.cotations.configuration

import fr.fabien.api.cotations.service.ServiceJWT
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
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
//@SecurityScheme(
//    name = "JWT Bearer Authentication",
//    type = SecuritySchemeType.HTTP,
//    bearerFormat = "JWT",
//    scheme = "bearer"
//)
@OpenAPIDefinition(
    info = Info(
        title = "API bourse",
        version = "\${server.servlet.context-path}",
        contact = Contact(
            name = "Fabien Rigaux", email = "Fabien.Rigaux@free.fr"
        ),
        license = License(
            name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
        ),
        description = "Valeurs et cours"
    ),
    servers = [Server(
        url = "\${api.server.url}\${server.servlet.context-path}",
        description = "\${api.server.description}"
    )]
)
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
        val securitySchemeName: String = "bearerAuth";
        return OpenAPI()
            .addSecurityItem(
                SecurityRequirement()
                    .addList(securitySchemeName)
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        securitySchemeName, SecurityScheme()
                            .name(securitySchemeName)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}
