package fr.fabien.api.cotations.restcontroller.dto.dcpuv

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "\${dto.DtoDcpuvLightCours.description}")
data class DtoDcpuvLightCours(
    @field:Schema(
        description = "\${dto.DtoDcpuvLightCours.field.date}", example = "2025-02-21", required = true,
        format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    @field:NotBlank
    val date: String,
    @field:Schema(description = "\${dto.DtoDcpuvLightCours.field.cloture}", example = "37.835", required = true)
    @field:NotNull
    val cloture: Double,
    @field:Schema(description = "\${dto.DtoDcpuvLightCours.field.volume}", example = "3252367", required = true)
    @field:NotNull
    val volume: Long,
    @field:Schema(description = "\${dto.DtoDcpuvLightCours.field.alerte}", required = true)
    @field:NotNull
    val alerte: Boolean
)