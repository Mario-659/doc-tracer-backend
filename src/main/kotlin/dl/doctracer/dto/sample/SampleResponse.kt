package dl.doctracer.dto.sample

import java.time.Instant

class SampleResponse (
    val id: Int,
    val description: String,
    val measurementDate: Instant,
    val spectrumTypeName: String,
    val coveredMaterialId: Int,
    val coveredMaterialName: String,
    val deviceName: String,
    val deviceId: Int,
    val sampleId: Int,
    val sampleCreationDate: Instant,
    val createdBy: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
