package dl.doctracer.dto.sample

import dl.doctracer.model.SpectralType
import java.time.Instant

data class SampleResponse(
    val id: Int,
    val measurementId: Int,
    val name: String,
    val spectralData: String?,
    val type: SpectralType,
    val createdAt: Instant,
    val updatedAt: Instant
)
