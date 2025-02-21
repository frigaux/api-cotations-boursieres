package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvCours
import fr.fabien.api.cotations.restcontroller.dto.dctv.DtoDctvCours
import fr.fabien.api.cotations.restcontroller.dto.dctv.DtoDctvWrapper
import fr.fabien.jpa.cotations.entity.Cours
import fr.fabien.jpa.cotations.entity.Valeur
import fr.fabien.jpa.cotations.repository.RepositoryCours
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("bourse/cours")
class RestControllerCours(
    private val repositoryValeur: RepositoryValeur,
    private val repositoryCours: RepositoryCours
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDernierCoursToutesValeurs(): DtoDctvWrapper {
        val valeurs: List<Valeur> = repositoryValeur.queryJoinLastCours()
        val date: String = valeurs.get(0)
            .cours.elementAt(0)
            .date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        return DtoDctvWrapper(
            date,
            valeurs.map { valeur ->
                val cours: Cours = valeur.cours.elementAt(0)
                DtoDctvCours(
                    valeur.ticker, cours.ouverture, cours.plusHaut,
                    cours.plusBas, cours.cloture, cours.volume,
                    cours.moyennesMobiles, cours.alerte
                )
            })
    }

    @GetMapping(value = ["{ticker}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDernierCoursPourUneValeur(@PathVariable ticker: String): DtoDcpuvCours {
        return repositoryCours.queryLastByTicker(ticker)
            .let {
                DtoDcpuvCours(
                    it.date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    it.ouverture, it.plusHaut,
                    it.plusBas, it.cloture, it.volume,
                    it.moyennesMobiles, it.alerte
                )
            }
    }

    @GetMapping(value = ["{ticker}/{limit}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDerniersCoursPourUneValeur(@PathVariable ticker: String, @PathVariable limit: Int): List<DtoDcpuvCours> {
        return repositoryCours.queryLatestByTicker(ticker, limit)
            .map {
                cours ->
                DtoDcpuvCours(
                    cours.date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    cours.ouverture, cours.plusHaut,
                    cours.plusBas, cours.cloture, cours.volume,
                    cours.moyennesMobiles, cours.alerte
                )
            }
    }
}