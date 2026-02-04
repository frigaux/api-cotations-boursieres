package fr.fabien.api.cotations.restcontroller.exceptions.handlers

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Description d'une erreur.")
data class ClientError(
    @field:Schema(description = "Description", example = "NOT_FOUND", required = true)
    @NotBlank
    val message: String
)
