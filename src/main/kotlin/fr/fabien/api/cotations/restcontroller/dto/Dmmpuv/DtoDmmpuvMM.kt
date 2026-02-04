package fr.fabien.api.cotations.restcontroller.dto.Dmmpuv

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

@Schema(description = "\${dto.DtoDmmpuvMM.description}")
data class DtoDmmpuvMM(
    @field:Schema(
        description = "\${dto.DtoDmmpuvMM.field.date}", example = "2025-02-21", required = true,
        format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    @field:NotBlank
    @field:Pattern(regexp = "$[0-9]{4}-[0-9]{2}-[0-9]{2}^", message = "format attendu (ISO8601) : YYYY-MM-DD")
    val date: String,
    @field:Schema(description = "\${dto.DtoDmmpuvMM.field.mm}", example = "37.835", required = true)
    @field:NotNull
    val mm: Double
)