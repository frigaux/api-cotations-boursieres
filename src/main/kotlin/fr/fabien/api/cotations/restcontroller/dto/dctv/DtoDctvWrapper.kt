package fr.fabien.api.cotations.restcontroller.dto.dctv

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

@Schema(description = "Le dernier cours des valeurs.")
data class DtoDctvWrapper(
    @field:Schema(
        description = "la date du dernier cours", example = "2025-02-21", required = true,
        format = "date", pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    )
    @NotBlank
    val date: String,
    @field:ArraySchema(schema = Schema(implementation = DtoDctvCours::class), minItems = 1)
    @NotEmpty
    val cours: List<DtoDctvCours>
)
