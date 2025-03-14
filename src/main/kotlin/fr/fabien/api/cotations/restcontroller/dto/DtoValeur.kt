package fr.fabien.api.cotations.restcontroller.dto

import fr.fabien.jpa.cotations.Marche
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "Une valeur.")
data class DtoValeur(
    @field:Schema(description = "le ticker", example = "GLE", required = true, pattern = "[A-Z0-9]{1,5}")
    @NotBlank
    @field:Size(max = 5)
    val ticker: String,
    @field:Schema(description = "le marché", example = "EURO_LIST_A", required = true)
    @NotNull
    val marche: Marche,
    @field:Schema(description = "le libellé", example = "Societe Generale", required = true)
    @NotBlank
    @field:Size(max = 100)
    val libelle: String
)
