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
class ConfigurationTest(
    @Autowired private val repositoryValeur: RepositoryValeur,
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
                valeur, LocalDate.now().minusDays(1), 37.76, 38.16, 37.64, 37.76,
                3551503, mutableListOf(37.76), false
            )
        )

        repositoryCours.save(
            Cours(
                valeur, LocalDate.now(), 37.795, 38.35, 37.47, 37.47,
                3058833, mutableListOf(37.47, 37.615), false
            )
        )
    }
}