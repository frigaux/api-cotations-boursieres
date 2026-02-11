package fr.fabien.api.cotations.restcontroller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "\${dto.DtoAuthentification.description}")
data class DtoAuthentification(
    @field:Schema(description = "\${dto.DtoAuthentification.field.identifiant}", example = "anonyme", required = true)
    @field:NotBlank
    @field:Size(min = 3, max = 20)
    val identifiant: String,
    @field:Schema(description = "\${dto.DtoAuthentification.field.motDePasse}", example = "anonyme", required = true)
    @field:NotBlank
    @field:Size(min = 8, max = 20)
    val motDePasse: String
)
