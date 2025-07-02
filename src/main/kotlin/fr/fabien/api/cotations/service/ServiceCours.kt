package fr.fabien.api.cotations.service

import fr.fabien.api.cotations.restcontroller.dto.Dmmpuv.DtoDmmpuvMM
import fr.fabien.api.cotations.restcontroller.dto.dcppv.DtoDcppvCours
import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvCours
import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvCoursAllege
import fr.fabien.api.cotations.restcontroller.dto.dctv.DtoDctvCours
import fr.fabien.api.cotations.restcontroller.dto.dctv.DtoDctvWrapper
import fr.fabien.api.cotations.restcontroller.exception.NotFoundException
import fr.fabien.jpa.cotations.entity.Cours
import fr.fabien.jpa.cotations.entity.Valeur
import fr.fabien.jpa.cotations.repository.RepositoryCours
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class ServiceCours(
    private val repositoryValeur: RepositoryValeur,
    private val repositoryCours: RepositoryCours
) {
    @Cacheable("getDerniersCoursToutesValeurs")
    fun getDerniersCoursToutesValeurs(): DtoDctvWrapper {
        val valeurs: List<Valeur> = repositoryValeur.queryJoinLastCours()
        val date: String = valeurs.get(0) // assertion cannot raise IndexOutOfBoundsException
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

    @Cacheable("getDernierCoursPourUneValeur")
    fun getDernierCoursPourUneValeur(ticker: String): DtoDcpuvCours {
        return repositoryCours.queryLastByTicker(ticker)
            ?.let {
                DtoDcpuvCours(
                    it.date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    it.ouverture, it.plusHaut,
                    it.plusBas, it.cloture, it.volume,
                    it.moyennesMobiles, it.alerte
                )
            }
            ?: run { throw NotFoundException() }
    }

    @Cacheable("getDerniersCoursPourUneValeur")
    fun getDerniersCoursPourUneValeur(ticker: String, limit: Int): List<DtoDcpuvCoursAllege> {
        return repositoryCours.queryLatestLightByTicker(ticker, limit.coerceAtMost(300))
            .map { objects ->
                DtoDcpuvCoursAllege(
                    (objects[0] as LocalDate).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    objects[1] as Double,
                    objects[2] as Long,
                    objects[3] as Boolean
                )
            }
    }

    @Cacheable("getDernieresMoyennesMobilesPourUneValeur")
    fun getDernieresMoyennesMobilesPourUneValeur(ticker: String, limit: Int, nbJoursMM: Int): List<DtoDmmpuvMM> {
        return repositoryCours.queryLatestByTicker(ticker, limit.coerceAtMost(300))
            .filter { cours -> cours.moyennesMobiles.size >= nbJoursMM }
            .map { cours ->
                DtoDmmpuvMM(
                    (cours.date).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    cours.moyennesMobiles[nbJoursMM - 1]
                )
            }
    }

    @Cacheable("getDerniersCoursPourPlusieursValeurs")
    fun getDerniersCoursPourPlusieursValeurs(tickers: Set<String>, limit: Int): List<DtoDcppvCours> {
        val coursAllegeByTicker: Map<String, List<DtoDcpuvCoursAllege>> =
            repositoryCours.queryLatestLightByTickers(tickers, limit * tickers.size)
                .groupBy({ objects -> objects[0] as String }, { objects ->
                    DtoDcpuvCoursAllege(
                        (objects[1] as LocalDate).format(DateTimeFormatter.ISO_LOCAL_DATE),
                        objects[2] as Double,
                        objects[3] as Long,
                        objects[4] as Boolean
                    )
                })
        return repositoryCours.queryLastByTickers(tickers).map { cours ->
            DtoDcppvCours(
                (cours.date).format(DateTimeFormatter.ISO_LOCAL_DATE),
                cours.valeur.ticker,
                cours.ouverture, cours.plusHaut,
                cours.plusBas, cours.cloture, cours.volume,
                cours.moyennesMobiles, cours.alerte, coursAllegeByTicker.get(cours.valeur.ticker)!!
            )
        }
    }
}