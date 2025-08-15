package fr.fabien.api.cotations.restcontroller.dto.dctv

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Schema(description = "\${dto.DtoDctvCours.description}")
data class DtoDctvCours(
    @field:Schema(description = "\${dto.DtoDctvCours.field.ticker}", example = "GLE", required = true, pattern = "[A-Z0-9]{1,5}")
    @field:NotBlank
    @field:Size(max = 5)
    val ticker: String,
    @field:Schema(description = "\${dto.DtoDctvCours.field.ouverture}", example = "37.79", required = true)
    @field:NotNull
    val ouverture: Double,
    @field:Schema(description = "\${dto.DtoDctvCours.field.plusHaut}", example = "38.035", required = true)
    @field:NotNull
    val plusHaut: Double,
    @field:Schema(description = "\${dto.DtoDctvCours.field.plusBas}", example = "37.545", required = true)
    @field:NotNull
    val plusBas: Double,
    @field:Schema(description = "\${dto.DtoDctvCours.field.cloture}", example = "37.835", required = true)
    @field:NotNull
    val cloture: Double,
    @field:Schema(description = "\${dto.DtoDctvCours.field.volume}", example = "3252367", required = true)
    @field:NotNull
    val volume: Long,
    @field:Schema(description = "\${dto.DtoDctvCours.field.moyennesMobiles}")
    @field:ArraySchema(
        schema = Schema(
            description = "cours", example = "37.835", required = true,
            type = "number", format = "double"
        ), minItems = 1, maxItems = 300
    )
    @field:NotEmpty
    val moyennesMobiles: MutableList<Double>,
)