package fr.fabien.api.cotations

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean


@SpringBootApplication
@EntityScan("fr.fabien")
@EnableCaching
class ApiBourse {
    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
        return CommandLineRunner { args: Array<String>? ->
            if (ctx.containsBean("dataSourceBusiness")) {
                val dataSourceBusiness: HikariDataSource = ctx.getBean("dataSourceBusiness") as HikariDataSource
                println(">>> ${dataSourceBusiness.javaClass.name}, connectionTimeout=${dataSourceBusiness.connectionTimeout}, maximumPoolSize=${dataSourceBusiness.maximumPoolSize}, ${dataSourceBusiness.credentials.username}:${dataSourceBusiness.credentials.password}@${dataSourceBusiness.jdbcUrl} (${dataSourceBusiness.driverClassName})")
            }
        }
    }
}

fun main(vararg args: String) {
    SpringApplicationBuilder()
        .sources(ApiBourse::class.java)
        .properties("spring.config.name:configuration")
        .run(*args)
}
