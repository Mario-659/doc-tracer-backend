package dl.doctracer.dto.sample

import dl.doctracer.model.SpectralType

data class CreateSampleRequest(
    val measurementId: Int,
    val name: String,
    val spectralData: List<DataPoint>,
    val type: SpectralType
)
