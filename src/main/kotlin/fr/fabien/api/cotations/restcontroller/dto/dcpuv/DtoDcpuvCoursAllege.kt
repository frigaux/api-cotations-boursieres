package fr.fabien.api.cotations.restcontroller.dto.dcpuv

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "\${dto.DtoDcpuvCoursAllege.description}")
data class DtoDcpuvCoursAllege(
    @field:Schema(
        description = "\${dto.DtoDcpuvCoursAllege.field.date}", example = "2025-02-21", required = true,
        format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    @field:NotBlank
    val date: String,
    @field:Schema(description = "\${dto.DtoDcpuvCoursAllege.field.cloture}", example = "37.835", required = true)
    @field:NotNull
    val cloture: Double,
    @field:Schema(description = "\${dto.DtoDcpuvCoursAllege.field.volume}", example = "3252367", required = true)
    @field:NotNull
    val volume: Long,
)