package dl.doctracer.dto.sample

import dl.doctracer.model.SpectralType
import dl.doctracer.validation.UniqueWavelengths
import jakarta.validation.Valid

data class CreateSampleRequest(
    val measurementId: Int,
    val name: String,
    @field:UniqueWavelengths
    @field:Valid
    val spectralData: List<@Valid DataPoint>,
    val type: SpectralType
)
