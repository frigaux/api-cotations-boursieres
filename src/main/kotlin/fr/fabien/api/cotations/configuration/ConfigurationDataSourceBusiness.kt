package fr.fabien.api.cotations.configuration

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties
import org.springframework.boot.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(
    basePackages = ["fr.fabien.jpa.cotations"],
    entityManagerFactoryRef = "entityManagerFactoryBusiness",
    transactionManagerRef = "transactionManagerBusiness"
)
@Profile("!test")
class ConfigurationDataSourceBusiness {

    @Bean
    @Primary
    @ConfigurationProperties("business.datasource")
    fun dataSourcePropertiesBusiness(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @Primary
    @ConfigurationProperties("business.datasource.connection-pool")
    fun dataSourceBusiness(@Qualifier("dataSourcePropertiesBusiness") properties: DataSourceProperties): DataSource {
        return properties.initializeDataSourceBuilder().build()
    }

    @Bean
    @Primary
    fun entityManagerFactoryBusiness(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("dataSourceBusiness") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages("fr.fabien.jpa.cotations")
            .persistenceUnit("persistenceUnitBusiness")
            .build()
    }

    @Bean
    @Primary
    fun transactionManagerBusiness(
        @Qualifier("entityManagerFactoryBusiness") entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }
}
