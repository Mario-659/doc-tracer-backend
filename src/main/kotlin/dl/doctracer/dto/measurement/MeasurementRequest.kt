package dl.doctracer.dto.measurement

import java.time.Instant

data class MeasurementRequest(
    val coveringMaterialId: Int,
    val coveredMaterialId: Int,
    val userId: Int,
    val deviceId: Int,
    val conditionsId: Int,
    val comments: String?,
    val measurementDate: Instant
)