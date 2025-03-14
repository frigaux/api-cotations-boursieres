package fr.fabien.api.cotations

import fr.fabien.jpa.cotations.Marche
import fr.fabien.jpa.cotations.entity.Cours
import fr.fabien.jpa.cotations.entity.Valeur
import fr.fabien.jpa.cotations.repository.RepositoryCours
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class ConfigurationTest(@Autowired private val repositoryValeur: RepositoryValeur,
                        @Autowired private val repositoryCours: RepositoryCours
) {
    companion object {
        val TICKER = "GLE"
    }
    init {
        val valeur: Valeur = Valeur(TICKER, Marche.EURO_LIST_A, "Societe Generale", setOf())
        repositoryValeur.save(valeur)

        repositoryCours.save(
            Cours(
                valeur, LocalDate.now().minusDays(1), 36.895, 37.03, 36.65, 36.925,
                2597395, mutableListOf(), false
            )
        )

        repositoryCours.save(
            Cours(
                valeur, LocalDate.now(), 36.8, 37.0, 36.6, 36.9,
                2500000, mutableListOf(), false
            )
        )
    }
}