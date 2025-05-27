package fr.fabien.api.cotations

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan("fr.fabien")
@EnableJpaRepositories("fr.fabien")
@EnableCaching
class ApiBourse {
}

fun main(vararg args: String) {
    SpringApplicationBuilder()
        .sources(ApiBourse::class.java)
        .properties("spring.config.name:configuration")
        .run(*args)
}
