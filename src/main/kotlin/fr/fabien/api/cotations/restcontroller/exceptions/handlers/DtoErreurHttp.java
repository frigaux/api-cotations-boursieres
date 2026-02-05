package fr.fabien.api.cotations.restcontroller.exceptions.handlers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "${dto.DtoErreurHttp.description}")
public class DtoErreurHttp {
    @Schema(description = "${dto.DtoErreurHttp.field.details}", example = "description de l'erreur", required = true)
    @NotBlank
    public String details;

    public DtoErreurHttp(String details) {
        this.details = details;
    }
}
