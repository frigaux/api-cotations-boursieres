package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.restcontroller.dto.DtoCours
import fr.fabien.jpa.cotations.entity.Cours
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("bourse/cours")
class RestControllerCours(private val repositoryValeur: RepositoryValeur) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCours(): List<DtoCours> {
        return repositoryValeur.queryJoinCoursByDate(LocalDate.now())
            .map { valeur ->
                val cours: Cours = valeur.cours.elementAt(0)
                DtoCours(
                    valeur.ticker, cours.ouverture, cours.plusHaut,
                    cours.plusBas, cours.cloture, cours.volume,
                    cours.moyennesMobiles, cours.alerte
                )
            }
    }
}