package dl.doctracer.dto.sample

import dl.doctracer.model.SpectralType
import dl.doctracer.validation.UniqueWavelengths
import jakarta.validation.Valid

data class UpdateSampleRequest(
    val name: String?,
    @field:UniqueWavelengths
    @field:Valid
    val spectralData: List<@Valid DataPoint>?,
    val type: SpectralType?,
    val measurementId: Int?
)
