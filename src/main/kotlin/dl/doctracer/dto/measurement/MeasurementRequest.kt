package dl.doctracer.dto.measurement

import dl.doctracer.model.MeasurementConditions
import java.time.Instant

data class MeasurementRequest(
    val coveringMaterialId: Int,
    val coveredMaterialId: Int,
    val username: String,
    val deviceId: Int,
    val conditions: MeasurementConditions,
    val comments: String?,
    val measurementDate: Instant
)