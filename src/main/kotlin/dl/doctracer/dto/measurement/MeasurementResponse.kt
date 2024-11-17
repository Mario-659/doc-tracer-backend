package dl.doctracer.dto.measurement

import java.time.Instant

data class MeasurementResponse(
    val id: Int,
    val coveringMaterial: String,
    val coveredMaterial: String,
    val user: String,
    val device: String,
    val conditions: MeasurementConditionsResponse,
    val comments: String?,
    val measurementDate: Instant,
    val createdAt: Instant,
    val updatedAt: Instant
)
