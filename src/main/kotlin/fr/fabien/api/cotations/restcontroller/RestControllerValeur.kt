package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.configuration.ConfigurationSecurity.Companion.SECURITY_SCHEME_NAME
import fr.fabien.api.cotations.restcontroller.dto.DtoValeur
import fr.fabien.api.cotations.restcontroller.exception.ClientError
import fr.fabien.api.cotations.service.ServiceValeur
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "\${api.valeurs.name}")
@RestController
@RequestMapping("bourse/valeurs")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
class RestControllerValeur(private val serviceValeur: ServiceValeur) {

    @Operation(summary = "\${api.valeurs.operation.getValeurs.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "\${api.valeurs.operation.getValeurs.response[200]}",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = DtoValeur::class), minItems = 1)
                )]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getValeurs(authentication: Authentication): List<DtoValeur> {
        return serviceValeur.getValeurs()
    }

    @Operation(summary = "\${api.valeurs.operation.getValeur.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "\${api.valeurs.operation.getValeur.response[200]}",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = DtoValeur::class)
                )]
            ),
            ApiResponse(
                responseCode = "404", description = "\${api.valeurs.operation.getValeur.response[404]}",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ClientError::class)
                )]
            )
        ]
    )
    @GetMapping(value = ["{ticker}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getValeur(
        authentication: Authentication,
        @Parameter(description = "\${api.valeurs.operation.getValeur.parameter.ticker}", required = true, example = "GLE")
        @PathVariable ticker: String
    ): DtoValeur {
        return serviceValeur.getValeur(ticker)
    }
}