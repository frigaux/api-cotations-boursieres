package fr.fabien.api.cotations

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

// TODO : tests
// TODO : swagger
// TODO : JWT with anonymous
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
