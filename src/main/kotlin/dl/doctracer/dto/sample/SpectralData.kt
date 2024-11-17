package dl.doctracer.dto.sample

import dl.doctracer.validation.UniqueWavelengths
import jakarta.validation.Valid
import jakarta.validation.constraints.*

data class SpectralData(
    @field:UniqueWavelengths
    @field:Valid
    val dataPoints: List<@Valid DataPoint>
)

data class DataPoint(
    @field:Min(400)
    @field:Max(1000)
    val wavelength: Int,

    @field:DecimalMin("0.0", inclusive = true)
    val intensity: Double
)
