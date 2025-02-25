package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.restcontroller.dto.DtoValeur
import fr.fabien.api.cotations.restcontroller.exception.ClientError
import fr.fabien.api.cotations.restcontroller.exception.NotFoundException
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "API des valeurs")
@RestController
@RequestMapping("bourse/valeurs")
class RestControllerValeur(private val repositoryValeur: RepositoryValeur) {

    @Operation(summary = "Récupère les valeurs")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Les valeurs",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = DtoValeur::class), minItems = 1)
                )]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getValeurs(): List<DtoValeur> {
        return repositoryValeur.findAll()
            .map { valeur -> DtoValeur(valeur.ticker, valeur.marche, valeur.libelle) }
    }

    @Operation(summary = "Récupère une valeur identifiée par son ticker")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "La valeur",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = DtoValeur::class)
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "Ticker introuvable",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ClientError::class)
                )]
            )
        ]
    )
    @GetMapping(value = ["{ticker}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getValeur(
        @Parameter(description = "ticker identifiant la valeur", required = true, example = "GLE")
        @PathVariable ticker: String
    ): DtoValeur {
        return repositoryValeur.findByTicker(ticker)
            ?.let { valeur -> DtoValeur(valeur.ticker, valeur.marche, valeur.libelle) }
            ?: run { throw NotFoundException() }
    }
}