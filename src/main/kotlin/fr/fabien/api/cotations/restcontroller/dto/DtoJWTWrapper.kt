package fr.fabien.api.cotations.restcontroller.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "JsonWebToken.")
data class DtoJWTWrapper(
    @field:Schema(description = "le JWT", example = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3NDExMDkwNzQsImlhdCI6MTc0MTEwODk4OCwiaWRlbnRpZmlhbnQiOiJhbm9ueW1vdXMifQ.8gFpQthr2G0Y_OtZRJ8awL0LRpIDADGBlqpgVwIqvb_b81VCVAdxHnwH1_bE9KtNmEiWZ0xgEzPteZ-HUnNRtA", required = true)
    @field:NotBlank
    @field:Size(max = 5000)
    val jwt: String
)
