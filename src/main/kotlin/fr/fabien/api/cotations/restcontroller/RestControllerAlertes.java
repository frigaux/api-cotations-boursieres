package fr.fabien.api.cotations.restcontroller;

import fr.fabien.api.cotations.restcontroller.dto.DtoAlerteC;
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
import org.springframework.security.core.Authentication;
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
                                            array = @ArraySchema(schema = @Schema(implementation = DtoAlerteC.class))
                                    )
                            }
                    )
            }
    )
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    private List<DtoAlerteC> alertes(Authentication authentication) {
        return serviceAlertes.alertes();
    }

    // TODO : documenter les 400 de @Validated et 404 de NotFoundException
    @Operation(summary = "${api.alertes.operation.creerAlerte.summary}")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = "${api.alertes.operation.creerAlerte.response[200]}",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = DtoAlerteC.class))
                                    )
                            }
                    )
            }
    )
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    private DtoAlerteC creerAlerte(@Validated @RequestBody DtoAlerteC dtoAlerte) {
        return serviceAlertes.creerAlerte(dtoAlerte);
    }
}
