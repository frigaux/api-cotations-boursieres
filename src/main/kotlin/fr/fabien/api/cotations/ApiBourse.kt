package fr.fabien.api.cotations

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

// TODO : externaliser la configuration sensible : mdp base et secret JWT (API + aspirateur)
// TODO : Swagger bearer + pas de bearer sur la resource authentification + 1 swagger par controller ? + améliorer la documentation du swagger
@SpringBootApplication
@EntityScan("fr.fabien")
@EnableJpaRepositories("fr.fabien")
class ApiBourse {
}

fun main(vararg args: String) {
    SpringApplicationBuilder()
        .sources(ApiBourse::class.java)
        .properties("spring.config.name:configuration")
        .run(*args)
}
