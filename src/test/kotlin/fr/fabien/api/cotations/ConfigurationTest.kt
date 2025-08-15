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
        val TICKER_GLE = "GLE"
        val TICKER_BNP = "BNP"
    }

    init {
        val valeurGLE: Valeur = Valeur(TICKER_GLE, Marche.EURO_LIST_A, "Societe Generale", setOf())
        repositoryValeur.save(valeurGLE)

        repositoryCours.save(
            Cours(
                valeurGLE, LocalDate.now().minusDays(1), 37.76, 38.16, 37.64, 37.76,
                3551503, mutableListOf(37.76)
            )
        )

        repositoryCours.save(
            Cours(
                valeurGLE, LocalDate.now(), 37.795, 38.35, 37.47, 37.47,
                3058833, mutableListOf(37.47, 37.615)
            )
        )

        val valeurBNP: Valeur = Valeur(TICKER_BNP, Marche.EURO_LIST_A, "Bnp Paribas", setOf())
        repositoryValeur.save(valeurBNP)

        repositoryCours.save(
            Cours(
                valeurBNP, LocalDate.now().minusDays(1), 70.89, 71.2, 69.97, 70.3,
                2497449, mutableListOf()
            )
        )

        repositoryCours.save(
            Cours(
                valeurBNP, LocalDate.now(), 70.37, 71.54, 70.37, 70.87,
                3309017, mutableListOf()
            )
        )
    }
}