package fr.fabien.api.cotations

import fr.fabien.jpa.cotations.entities.Alerte
import fr.fabien.jpa.cotations.entities.Cours
import fr.fabien.jpa.cotations.entities.Valeur
import fr.fabien.jpa.cotations.enumerations.Marche
import fr.fabien.jpa.cotations.enumerations.TypeAlerte
import fr.fabien.jpa.cotations.enumerations.TypeNotification
import fr.fabien.jpa.cotations.repository.RepositoryAlerte
import fr.fabien.jpa.cotations.repository.RepositoryCours
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class ConfigurationTest(
    @Autowired private val repositoryValeur: RepositoryValeur,
    @Autowired private val repositoryCours: RepositoryCours,
    @Autowired private val repositoryAlerte: RepositoryAlerte
) {
    companion object {
        val TICKER_GLE = "GLE"
        val TICKER_BNP = "BNP"
        lateinit var VALEUR_GLE: Valeur
        lateinit var VALEUR_BNP: Valeur
    }

    init {
        saveValeurs()
        saveCours()
        saveAlertes()
    }

    private fun saveValeurs() {
        VALEUR_GLE = Valeur(TICKER_GLE, Marche.EURO_LIST_A, "Societe Generale", setOf())
        repositoryValeur.save(VALEUR_GLE)
        VALEUR_BNP = Valeur(TICKER_BNP, Marche.EURO_LIST_A, "Bnp Paribas", setOf())
        repositoryValeur.save(VALEUR_BNP)
    }

    private fun saveCours() {
        repositoryCours.save(
            Cours(
                VALEUR_GLE, LocalDate.now().minusDays(1), 37.76, 38.16, 37.64, 37.76,
                3551503, mutableListOf(37.76)
            )
        )

        repositoryCours.save(
            Cours(
                VALEUR_GLE, LocalDate.now(), 37.795, 38.35, 37.47, 37.47,
                3058833, mutableListOf(37.47, 37.615)
            )
        )

        repositoryCours.save(
            Cours(
                VALEUR_BNP, LocalDate.now().minusDays(1), 70.89, 71.2, 69.97, 70.3,
                2497449, mutableListOf()
            )
        )

        repositoryCours.save(
            Cours(
                VALEUR_BNP, LocalDate.now(), 70.37, 71.54, 70.37, 70.87,
                3309017, mutableListOf()
            )
        )
    }

    private fun saveAlertes() {
        repositoryAlerte.save(
            Alerte(
                VALEUR_GLE, "la clôture est au plus bas ces 20 derniers jours",
                TypeAlerte.PLUS_BAS, "CLOTURE(1) = PLUS_BAS(20)", null, true,
                TypeNotification.SYSTEME
            )
        )

        repositoryAlerte.save(
            Alerte(
                VALEUR_GLE, "la clôture est au plus haut ces 50 derniers jours",
                TypeAlerte.PLUS_HAUT, "CLOTURE(1) = PLUS_HAUT(50)", null, true,
                TypeNotification.SYSTEME
            )
        )
    }
}