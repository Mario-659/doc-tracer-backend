package dl.doctracer.dto.sample

import jakarta.validation.constraints.*

data class DataPoint(
    @field:Min(400)
    @field:Max(1000)
    val wavelength: Int,

    @field:DecimalMin("0.0", inclusive = true)
    val intensity: Double
)
