package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.configuration.ConfigurationSecurity.Companion.SECURITY_SCHEME_NAME
import fr.fabien.api.cotations.restcontroller.dto.Dmmpuv.DtoDmmpuvMM
import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvCours
import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvLightCours
import fr.fabien.api.cotations.restcontroller.dto.dctv.DtoDctvCours
import fr.fabien.api.cotations.restcontroller.dto.dctv.DtoDctvWrapper
import fr.fabien.api.cotations.restcontroller.exception.ClientError
import fr.fabien.api.cotations.restcontroller.exception.NotFoundException
import fr.fabien.jpa.cotations.entity.Cours
import fr.fabien.jpa.cotations.entity.Valeur
import fr.fabien.jpa.cotations.repository.RepositoryCours
import fr.fabien.jpa.cotations.repository.RepositoryValeur
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Tag(name = "\${api.cours.name}")
@RestController
@RequestMapping("bourse/cours")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
class RestControllerCours(
    private val repositoryValeur: RepositoryValeur,
    private val repositoryCours: RepositoryCours
) {

    @Operation(summary = "\${api.cours.operation.getDerniersCours.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "\${api.cours.operation.getDerniersCours.response[200]}",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = DtoDctvWrapper::class)
                )]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getDerniersCoursToutesValeurs(authentication: Authentication): DtoDctvWrapper {
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


    @Operation(summary = "\${api.cours.operation.getDernierCoursPourUneValeur.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "\${api.cours.operation.getDernierCoursPourUneValeur.response[200]}",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = DtoDcpuvCours::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "\${api.cours.operation.getDernierCoursPourUneValeur.response[404]}",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ClientError::class)
                )]
            )
        ]
    )
    @GetMapping(value = ["{ticker}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getDernierCoursPourUneValeur(
        authentication: Authentication,
        @Parameter(
            description = "\${api.cours.operation.getDernierCoursPourUneValeur.parameter.ticker}",
            required = true,
            example = "GLE"
        )
        @PathVariable ticker: String
    ): DtoDcpuvCours {
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

    @Operation(summary = "\${api.cours.operation.getDerniersCoursPourUneValeur.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "\${api.cours.operation.getDerniersCoursPourUneValeur.response[200]}",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = DtoDcpuvLightCours::class), minItems = 1)
                )]
            )
        ]
    )
    @GetMapping(value = ["{ticker}/{limit}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getDerniersCoursPourUneValeur(
        authentication: Authentication,
        @Parameter(
            description = "\${api.cours.operation.getDerniersCoursPourUneValeur.parameter.ticker}",
            required = true,
            example = "GLE"
        )
        @PathVariable ticker: String,
        @Parameter(
            description = "\${api.cours.operation.getDerniersCoursPourUneValeur.parameter.limit}",
            required = true,
            example = "30"
        )
        @Min(1)
        @Max(300)
        @PathVariable limit: Int
    ): List<DtoDcpuvLightCours> {
        return repositoryCours.queryLatestLightByTicker(ticker, limit.coerceAtMost(300))
            .map { objects ->
                DtoDcpuvLightCours(
                    (objects[0] as LocalDate).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    objects[1] as Double,
                    objects[2] as Long,
                    objects[3] as Boolean
                )
            }
    }

    @Operation(summary = "\${api.cours.operation.getDernieresMoyennesMobilesPourUneValeur.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "\${api.cours.operation.getDernieresMoyennesMobilesPourUneValeur.response[200]}",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = DtoDmmpuvMM::class), minItems = 1)
                )]
            )
        ]
    )
    @GetMapping(value = ["{ticker}/{limit}/{nbJoursMM}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getDernieresMoyennesMobilesPourUneValeur(
        authentication: Authentication,
        @Parameter(
            description = "\${api.cours.operation.getDernieresMoyennesMobilesPourUneValeur.parameter.ticker}",
            required = true,
            example = "GLE"
        )
        @PathVariable ticker: String,
        @Parameter(
            description = "\${api.cours.operation.getDernieresMoyennesMobilesPourUneValeur.parameter.limit}",
            required = true,
            example = "30"
        )
        @Min(1)
        @Max(300)
        @PathVariable limit: Int,
        @Parameter(
            description = "\${api.cours.operation.getDernieresMoyennesMobilesPourUneValeur.parameter.nbJoursMM}",
            required = true,
            example = "20"
        )
        @Min(1)
        @Max(300)
        @PathVariable nbJoursMM: Int
    ): List<DtoDmmpuvMM> {
        return repositoryCours.queryLatestByTicker(ticker, limit.coerceAtMost(300))
            .filter { cours -> cours.moyennesMobiles.size >= nbJoursMM }
            .map { cours ->
                DtoDmmpuvMM(
                    (cours.date).format(DateTimeFormatter.ISO_LOCAL_DATE),
                    cours.moyennesMobiles[nbJoursMM - 1]
                )
            }
    }
}