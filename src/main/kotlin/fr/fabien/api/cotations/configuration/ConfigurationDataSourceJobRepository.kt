package fr.fabien.api.cotations.configuration

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.boot.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["fr.fabien.jpa.springbatch"],
    entityManagerFactoryRef = "entityManagerFactoryJobRepository",
    transactionManagerRef = "transactionManagerJobRepository"
)
@Profile("!test")
class ConfigurationDataSourceJobRepository {

    @Bean
    @ConfigurationProperties("job-repository.datasource")
    fun dataSourcePropertiesJobRepository(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @ConfigurationProperties("job-repository.datasource.connection-pool")
    fun dataSourceJobRepository(@Qualifier("dataSourcePropertiesJobRepository") properties: DataSourceProperties): DataSource {
        return properties.initializeDataSourceBuilder().build()
    }

    @Bean
    fun entityManagerFactoryJobRepository(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("dataSourceJobRepository") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages("fr.fabien.jpa.springbatch")
            .persistenceUnit("persistenceUnitJobRepository")
            .properties(
                mapOf<String, String>(
                    "hibernate.physical_naming_strategy" to
                            "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
                )
            )
            .build()
    }

    @Bean
    fun transactionManagerJobRepository(
        @Qualifier("entityManagerFactoryJobRepository") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}
