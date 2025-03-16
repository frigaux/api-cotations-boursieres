package fr.fabien.api.cotations.restcontroller.dto.dctv

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

@Schema(description = "\${dto.DtoDctvWrapper.description}")
data class DtoDctvWrapper(
    @field:Schema(
        description = "\${dto.DtoDctvWrapper.field.date}", example = "2025-02-21", required = true,
        format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    @field:NotBlank
    val date: String,
    @field:ArraySchema(schema = Schema(implementation = DtoDctvCours::class), minItems = 1)
    @field:NotEmpty
    val cours: List<DtoDctvCours>
)
