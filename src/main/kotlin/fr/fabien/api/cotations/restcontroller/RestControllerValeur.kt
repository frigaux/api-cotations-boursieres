package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.restcontroller.dto.DtoValeur
import fr.fabien.api.cotations.restcontroller.exception.NotFoundException
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull


@RestController
@RequestMapping("bourse/valeurs")
class RestControllerValeur(private val repositoryValeur: RepositoryValeur) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getValeurs(): List<DtoValeur> {
        return repositoryValeur.findAll()
            .map { valeur -> DtoValeur(valeur.ticker, valeur.marche, valeur.libelle) }
    }

    @GetMapping(value = ["{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getValeur(@PathVariable id: Int): DtoValeur {
        return repositoryValeur.findById(id).getOrNull()
            ?.let { valeur -> DtoValeur(valeur.ticker, valeur.marche, valeur.libelle) }
            ?: run { throw NotFoundException() }
    }
}