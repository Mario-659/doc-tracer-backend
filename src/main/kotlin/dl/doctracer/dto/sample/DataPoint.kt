package dl.doctracer.dto.sample

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.*

data class DataPoint @JsonCreator constructor(
    @field:Min(400)
    @field:Max(1000)
    @JsonProperty("wavelength")
    val wavelength: Int,

    @field:DecimalMin("0.0", inclusive = true)
    @JsonProperty("intensity")
    val intensity: Double
)
