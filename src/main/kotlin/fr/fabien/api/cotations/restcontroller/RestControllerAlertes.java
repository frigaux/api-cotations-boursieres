package fr.fabien.api.cotations.restcontroller;

import fr.fabien.api.cotations.restcontroller.dto.DtoAlerte;
import fr.fabien.api.cotations.restcontroller.dto.DtoAlerteAvecId;
import fr.fabien.api.cotations.restcontroller.exceptions.handlers.DtoErreurHttp;
import fr.fabien.api.cotations.service.ServiceAlertes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fr.fabien.api.cotations.restcontroller.RestControllerAlertes.SECURITY_SCHEME_NAME;

@Tag(name = "${api.alertes.name}")
@RestController
@RequestMapping("bourse/alertes")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
public class RestControllerAlertes {
    public static final String SECURITY_SCHEME_NAME = "JWT Bearer Authentication";

    @Autowired
    private ServiceAlertes serviceAlertes;

    @Operation(summary = "${api.alertes.operation.alertes.summary}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "${api.alertes.operation.alertes.response[200]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = DtoAlerteAvecId.class))
                                    )
                            }
                    )
            }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    private List<DtoAlerteAvecId> alertes() {
        return serviceAlertes.alertes();
    }

    @Operation(summary = "${api.alertes.operation.creerAlerte.summary}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "${api.alertes.operation.creerAlerte.response[200]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = DtoAlerteAvecId.class))
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "${api.alertes.operation.creerAlerte.response[404]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = DtoErreurHttp.class)
                                    )}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "${api.alertes.operation.creerAlerte.response[400]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = DtoErreurHttp.class)
                                    )}
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "${api.alertes.operation.creerAlerte.response[409]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = DtoErreurHttp.class)
                                    )}
                    )
            }
    )
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    private DtoAlerteAvecId creerAlerte(@Validated @RequestBody DtoAlerte dtoAlerte) {
        return serviceAlertes.creerAlerte(dtoAlerte);
    }

    @Operation(summary = "${api.alertes.operation.modifierAlerte.summary}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "${api.alertes.operation.modifierAlerte.response[200]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = DtoAlerteAvecId.class))
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "${api.alertes.operation.modifierAlerte.response[404]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = DtoErreurHttp.class)
                                    )}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "${api.alertes.operation.modifierAlerte.response[400]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = DtoErreurHttp.class)
                                    )}
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "${api.alertes.operation.modifierAlerte.response[409]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = DtoErreurHttp.class)
                                    )}
                    )
            }
    )
    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    private DtoAlerteAvecId modifierAlerte(@PathVariable Integer id, @Validated @RequestBody DtoAlerte dtoAlerte) {
        return serviceAlertes.modifierAlerte(id, dtoAlerte);
    }
}
