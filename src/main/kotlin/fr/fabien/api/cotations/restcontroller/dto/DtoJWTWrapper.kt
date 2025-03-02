package fr.fabien.api.cotations.restcontroller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "JsonWebToken.")
data class DtoJWTWrapper(
    @field:Schema(description = "le JWT", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm9ueW1vdXMiLCJpYXQiOjE3NDA5Mjk5MDgsImV4cCI6MTc0MDkyOTk5NX0.7Oo-fu1Q3xlnxhZ842Hb6q26osJRNk0pKRYIKajifTSp2Vs4AncFCXFO-vmO77nh_Ds8YPrHaxkCm0lua1UJiQ", required = true)
    @NotBlank
    @Size(max = 5000)
    val jwt: String
)
