package dl.doctracer.dto.sample

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import dl.doctracer.validation.UniqueWavelengths
import jakarta.validation.Valid

data class SpectralData @JsonCreator constructor(
    @field:UniqueWavelengths
    @field:Valid
    @JsonProperty("dataPoints")
    val dataPoints: List<@Valid DataPoint>
)
