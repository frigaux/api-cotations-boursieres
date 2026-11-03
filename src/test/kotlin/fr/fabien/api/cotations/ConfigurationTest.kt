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
import fr.fabien.jpa.springbatch.entities.JobExecution
import fr.fabien.jpa.springbatch.entities.JobExecutionParam
import fr.fabien.jpa.springbatch.entities.JobInstance
import fr.fabien.jpa.springbatch.entities.StepExecution
import fr.fabien.jpa.springbatch.repositories.RepositoryJobExecution
import fr.fabien.jpa.springbatch.repositories.RepositoryJobExecutionParam
import fr.fabien.jpa.springbatch.repositories.RepositoryJobInstance
import fr.fabien.jpa.springbatch.repositories.RepositoryStepExecution
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.*

@Configuration
@EnableJpaRepositories("fr.fabien.jpa")
class ConfigurationTest(
    @Autowired private val repositoryValeur: RepositoryValeur,
    @Autowired private val repositoryCours: RepositoryCours,
    @Autowired private val repositoryAlerte: RepositoryAlerte,
    @Autowired private val repositoryJobInstance: RepositoryJobInstance,
    @Autowired private val repositoryJobExecution: RepositoryJobExecution,
    @Autowired private val repositoryStepExecution: RepositoryStepExecution,
    @Autowired private val repositoryJobExecutionParam: RepositoryJobExecutionParam
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
        saveJobs()
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

    private fun saveJobs() {
        val job1 = JobInstance(1, "jobMajAbcLibelles", "jobMajAbcLibelles", Collections.emptySet());
        val job2 = JobInstance(1, "jobMajAbcCotations", "jobMajAbcCotations", Collections.emptySet());
        val execution1 = JobExecution(
            1,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "COMPLETED",
            "COMPLETED",
            "",
            OffsetDateTime.now(),
            job1,
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
        );
        val execution2 = JobExecution(
            1,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "COMPLETED",
            "COMPLETED",
            "",
            OffsetDateTime.now(),
            job2,
            Collections.emptySet(),
            Collections.emptySet(),
            Collections.emptySet()
        );
        val step1 = StepExecution(
            1,
            "stepRecupererAbcLibelles",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "COMPLETED",
            1,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            "COMPLETED",
            "",
            OffsetDateTime.now(),
            execution1,
            Collections.emptySet()
        );
        val step2 = StepExecution(
            1,
            "stepPersisterAbcLibelles",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "COMPLETED",
            34,
            355,
            0,
            355,
            0,
            0,
            0,
            0,
            "COMPLETED",
            "",
            OffsetDateTime.now(),
            execution1,
            Collections.emptySet()
        );
        val step3 = StepExecution(
            1,
            "stepRecupererAbcCotations",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "COMPLETED",
            1,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            "COMPLETED",
            "",
            OffsetDateTime.now(),
            execution2,
            Collections.emptySet()
        );
        val step4 = StepExecution(
            1,
            "stepPersisterAbcCotations",
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            "COMPLETED",
            31,
            300,
            0,
            300,
            0,
            0,
            0,
            0,
            "COMPLETED",
            "",
            OffsetDateTime.now(),
            execution2,
            Collections.emptySet()
        );
        val params1 = JobExecutionParam("date", "java.time.LocalDate", "2026-10-20", 'Y', execution1);
        val params2 = JobExecutionParam("date", "java.time.LocalDate", "2026-10-20", 'Y', execution2);

        repositoryJobInstance.save(job1);
        repositoryJobInstance.save(job2);
        repositoryJobExecution.save(execution1);
        repositoryJobExecution.save(execution2);
        repositoryStepExecution.save(step1);
        repositoryStepExecution.save(step2);
        repositoryStepExecution.save(step3);
        repositoryStepExecution.save(step4);
        repositoryJobExecutionParam.save(params1);
        repositoryJobExecutionParam.save(params2);
    }
}