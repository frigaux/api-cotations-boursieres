package fr.fabien.api.cotations.restcontroller.dto.dctv

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "Le cours d'une valeur.")
data class DtoDctvCours(
    @field:Schema(description = "le ticker", example = "GLE", required = true, pattern = "[A-Z0-9]{1,5}")
    @NotBlank
    @Size(max = 5)
    val ticker: String,
    @field:Schema(description = "cours à l'ouverture", example = "37.79", required = true)
    @NotNull
    val ouverture: Double,
    @field:Schema(description = "cours le plus haut", example = "38.035", required = true)
    @NotNull
    val plusHaut: Double,
    @field:Schema(description = "cours le plus bas", example = "37.545", required = true)
    @NotNull
    val plusBas: Double,
    @field:Schema(description = "cours à la clôture", example = "37.835", required = true)
    @NotNull
    val cloture: Double,
    @field:Schema(description = "volume échangé en nombre de titres", example = "3252367", required = true)
    @NotNull
    val volume: Long,
    @field:Schema(description = "les moyennes mobiles des cours pour la valeur (le premier élément correspond à la moyenne mobile sur 1 jour, cad le cours du jour)")
    @field:ArraySchema(
        schema = Schema(
            description = "cours", example = "37.835", required = true,
            type = "number", format = "double"
        ), minItems = 1, maxItems = 300
    )
    @NotEmpty
    val moyennesMobiles: MutableList<Double>,
    @field:Schema(description = "alerte changement haussier/baissier", required = true)
    @NotNull
    val alerte: Boolean
)