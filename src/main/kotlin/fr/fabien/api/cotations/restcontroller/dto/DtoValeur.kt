package fr.fabien.api.cotations.restcontroller.dto

import fr.fabien.jpa.cotations.Marche
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "\${dto.DtoValeur.description}")
data class DtoValeur(
    @field:Schema(description = "\${dto.DtoValeur.field.ticker}", example = "GLE", required = true, pattern = "[A-Z0-9]{1,5}")
    @NotBlank
    @field:Size(max = 5)
    val ticker: String,
    @field:Schema(description = "\${dto.DtoValeur.field.marche}", example = "EURO_LIST_A", required = true)
    @NotNull
    val marche: Marche,
    @field:Schema(description = "\${dto.DtoValeur.field.libelle}", example = "Societe Generale", required = true)
    @NotBlank
    @field:Size(max = 100)
    val libelle: String
)
