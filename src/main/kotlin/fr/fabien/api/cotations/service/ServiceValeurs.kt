package fr.fabien.api.cotations.service

import fr.fabien.api.cotations.restcontroller.dto.DtoValeur
import fr.fabien.api.cotations.restcontroller.exceptions.NotFoundException
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ServiceValeurs(private val repositoryValeur: RepositoryValeur) {

    @Cacheable("getValeurs")
    fun getValeurs(): List<DtoValeur> {
        return repositoryValeur.findAll()
            .map { valeur -> DtoValeur(valeur.ticker, valeur.marche, valeur.libelle) }
    }

    @Cacheable("getValeur")
    fun getValeur(ticker: String): DtoValeur {
        return repositoryValeur.findByTicker(ticker)
            ?.let { valeur -> DtoValeur(valeur.ticker, valeur.marche, valeur.libelle) }
            ?: run { throw NotFoundException("ticker introuvable") }
    }
}