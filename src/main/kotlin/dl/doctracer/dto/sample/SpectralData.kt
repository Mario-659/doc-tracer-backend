package dl.doctracer.dto.sample

import dl.doctracer.validation.UniqueWavelengths
import jakarta.validation.Valid

data class SpectralData(
    @field:UniqueWavelengths
    @field:Valid
    val dataPoints: List<@Valid DataPoint>
)
