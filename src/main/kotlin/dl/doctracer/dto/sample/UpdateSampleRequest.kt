package dl.doctracer.dto.sample

import dl.doctracer.model.SpectralType

data class UpdateSampleRequest(
    val name: String?,
    val spectralData: SpectralData?,
    val type: SpectralType?
)
