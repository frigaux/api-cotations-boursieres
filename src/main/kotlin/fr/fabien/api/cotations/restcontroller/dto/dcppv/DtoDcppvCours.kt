package fr.fabien.api.cotations.restcontroller.dto.dcppv

import fr.fabien.api.cotations.restcontroller.dto.dcpuv.DtoDcpuvCoursAllege
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Schema(description = "\${dto.DtoDcppvCours.description}")
data class DtoDcppvCours(
    @field:Schema(
        description = "\${dto.DtoDcppvCours.field.date}", example = "2025-02-21", required = true,
        format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    @field:NotBlank
    @field:Pattern(regexp = "$[0-9]{4}-[0-9]{2}-[0-9]{2}^", message = "format attendu (ISO8601) : YYYY-MM-DD")
    val date: String,
    @field:Schema(description = "\${dto.DtoDcppvCours.field.ticker}", example = "GLE", required = true, pattern = "[A-Z0-9]{1,5}")
    @field:NotBlank
    @field:Size(max = 5)
    val ticker: String,
    @field:Schema(description = "\${dto.DtoDcppvCours.field.ouverture}", example = "37.79", required = true)
    @field:NotNull
    val ouverture: Double,
    @field:Schema(description = "\${dto.DtoDcppvCours.field.plusHaut}", example = "38.035", required = true)
    @field:NotNull
    val plusHaut: Double,
    @field:Schema(description = "\${dto.DtoDcppvCours.field.plusBas}", example = "37.545", required = true)
    @field:NotNull
    val plusBas: Double,
    @field:Schema(description = "\${dto.DtoDcppvCours.field.cloture}", example = "37.835", required = true)
    @field:NotNull
    val cloture: Double,
    @field:Schema(description = "\${dto.DtoDcppvCours.field.volume}", example = "3252367", required = true)
    @field:NotNull
    val volume: Long,
    @field:Schema(description = "\${dto.DtoDcppvCours.field.moyennesMobiles}")
    @field:ArraySchema(
        schema = Schema(
            description = "cours", example = "37.835", required = true,
            type = "number", format = "double"
        ), minItems = 1, maxItems = 300
    )
    @field:NotEmpty
    val moyennesMobiles: MutableList<Double>,
    @field:ArraySchema(schema = Schema(implementation = DtoDcpuvCoursAllege::class), minItems = 1)
    @field:NotEmpty
    val cours: List<DtoDcpuvCoursAllege>
)