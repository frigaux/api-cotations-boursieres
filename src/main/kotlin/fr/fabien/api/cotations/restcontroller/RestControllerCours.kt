package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.restcontroller.RestControllerAlertes.SECURITY_SCHEME_NAME
import fr.fabien.api.cotations.restcontroller.dto.Dmmpuv.DtoDmmpuvMM
import fr.fabien.api.cotations.restcontroller.dto.dcppv.DtoDcppvCours
import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvCours
import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvCoursAllege
import fr.fabien.api.cotations.restcontroller.dto.dctv.DtoDctvWrapper
import fr.fabien.api.cotations.restcontroller.exception.ClientError
import fr.fabien.api.cotations.service.ServiceCours
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
import org.springframework.web.bind.annotation.*

@Tag(name = "\${api.cours.name}")
@RestController
@RequestMapping("bourse/cours")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
class RestControllerCours(private val serviceCours: ServiceCours) {

    @Operation(summary = "\${api.cours.operation.getDerniersCours.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "\${api.cours.operation.getDerniersCours.response[200]}",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = DtoDctvWrapper::class)
                )]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getDerniersCoursToutesValeurs(authentication: Authentication): DtoDctvWrapper {
        return serviceCours.getDerniersCoursToutesValeurs()
    }


    @Operation(summary = "\${api.cours.operation.getDernierCoursPourUneValeur.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "\${api.cours.operation.getDernierCoursPourUneValeur.response[200]}",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = DtoDcpuvCours::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "\${api.cours.operation.getDernierCoursPourUneValeur.response[404]}",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
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
        return serviceCours.getDernierCoursPourUneValeur(ticker)
    }

    @Operation(summary = "\${api.cours.operation.getDerniersCoursPourUneValeur.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "\${api.cours.operation.getDerniersCoursPourUneValeur.response[200]}",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = ArraySchema(schema = Schema(implementation = DtoDcpuvCoursAllege::class), minItems = 1)
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
    ): List<DtoDcpuvCoursAllege> {
        return serviceCours.getDerniersCoursPourUneValeur(ticker, limit)
    }

    @Operation(summary = "\${api.cours.operation.getDernieresMoyennesMobilesPourUneValeur.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "\${api.cours.operation.getDernieresMoyennesMobilesPourUneValeur.response[200]}",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = ArraySchema(schema = Schema(implementation = DtoDmmpuvMM::class), minItems = 1)
                )]
            )
        ]
    )
    @GetMapping(
        value = ["moyennes-mobiles/{ticker}/{limit}/{nbJoursMM}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
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
        return serviceCours.getDernieresMoyennesMobilesPourUneValeur(ticker, limit, nbJoursMM)
    }

    @Operation(summary = "\${api.cours.operation.getDerniersCoursPourPlusieursValeurs.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "\${api.cours.operation.getDerniersCoursPourPlusieursValeurs.response[200]}",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = ArraySchema(schema = Schema(implementation = DtoDcppvCours::class), minItems = 1)
                )]
            )
        ]
    )
    @GetMapping(value = ["tickers/{limit}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun getDerniersCoursPourPlusieursValeurs(
        authentication: Authentication,
        @Parameter(
            description = "\${api.cours.operation.getDerniersCoursPourPlusieursValeurs.parameter.limit}",
            required = true,
            example = "30"
        )
        @Min(1)
        @Max(300)
        @PathVariable limit: Int,
        @Parameter(
            description = "\${api.cours.operation.getDerniersCoursPourPlusieursValeurs.parameter.tickers}",
            required = true,
            example = "GLE, BNP"
        )
        @RequestParam tickers: Set<String>,
    ): List<DtoDcppvCours> {
        return serviceCours.getDerniersCoursPourPlusieursValeurs(tickers, limit)
    }
}