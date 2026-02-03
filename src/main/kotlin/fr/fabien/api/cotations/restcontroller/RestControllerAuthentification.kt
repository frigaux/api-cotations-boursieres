package fr.fabien.api.cotations.restcontroller

import fr.fabien.api.cotations.restcontroller.dto.DtoAuthentification
import fr.fabien.api.cotations.restcontroller.dto.DtoJWTWrapper
import fr.fabien.api.cotations.service.ServiceJWT
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "\${api.authentification.name}")
@RestController
@RequestMapping("bourse/authentification")
class RestControllerAuthentification(
    private val serviceJWT: ServiceJWT
) {
    @Operation(summary = "\${api.authentification.operation.generateJwtToken.summary}")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "\${api.authentification.operation.generateJwtToken.response[200]}",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = DtoJWTWrapper::class)
                )]
            )
        ]
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    private fun generateJwtToken(@Validated @RequestBody dtoAuthentification: DtoAuthentification): DtoJWTWrapper {
        return DtoJWTWrapper(serviceJWT.generateJwtToken(dtoAuthentification))
    }
}