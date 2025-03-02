package fr.fabien.api.cotations.restcontroller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Informations pour réaliser une authentification.")
data class DtoAuthentification(
    @field:Schema(description = "identifiant de l'utilisateur", example = "anonymous", required = true)
    @NotBlank
    @Size(max = 20)
    val identifiant: String,
    @field:Schema(description = "mot de passe de l'utilisateur", example = "anonymous", required = true)
    @NotBlank
    @Size(max = 20)
    val motDePasse: String
)
