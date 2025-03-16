package fr.fabien.api.cotations.restcontroller.dto.dcpuv

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

@Schema(description = "\${dto.DtoDcpuvCours.description}")
data class DtoDcpuvCours(
    @field:Schema(
        description = "\${dto.DtoDcpuvCours.field.date}", example = "2025-02-21", required = true,
        format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    @field:NotBlank
    val date: String,
    @field:Schema(description = "\${dto.DtoDcpuvCours.field.ouverture}", example = "37.79", required = true)
    @field:NotNull
    val ouverture: Double,
    @field:Schema(description = "\${dto.DtoDcpuvCours.field.plusHaut}", example = "38.035", required = true)
    @field:NotNull
    val plusHaut: Double,
    @field:Schema(description = "\${dto.DtoDcpuvCours.field.plusBas}", example = "37.545", required = true)
    @field:NotNull
    val plusBas: Double,
    @field:Schema(description = "\${dto.DtoDcpuvCours.field.cloture}", example = "37.835", required = true)
    @field:NotNull
    val cloture: Double,
    @field:Schema(description = "\${dto.DtoDcpuvCours.field.volume}", example = "3252367", required = true)
    @field:NotNull
    val volume: Long,
    @field:Schema(description = "\${dto.DtoDcpuvCours.field.moyennesMobiles}")
    @field:ArraySchema(
        schema = Schema(
            description = "cours", example = "37.835", required = true,
            type = "number", format = "double"
        ), minItems = 1, maxItems = 300
    )
    @field:NotEmpty
    val moyennesMobiles: MutableList<Double>,
    @field:Schema(description = "\${dto.DtoDcpuvCours.field.alerte}", required = true)
    @field:NotNull
    val alerte: Boolean
)