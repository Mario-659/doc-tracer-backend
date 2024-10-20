package dl.doctracer.dto.sample

import java.time.Instant

class SampleResponse (
    val id: Int,
    val description: String,
    val coveredMaterialId: Int,
    val coveredMaterialName: String,
    val coveringMaterialId: Int,
    val coveringMaterialName: String,
    val sampleCreationDate: Instant,
    val createdBy: String,
    val createdAt: Instant,
    val updatedAt: Instant
)
