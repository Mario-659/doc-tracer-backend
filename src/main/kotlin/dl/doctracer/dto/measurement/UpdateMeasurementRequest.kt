package dl.doctracer.dto.measurement

import dl.doctracer.model.MeasurementConditions
import java.time.Instant

data class UpdateMeasurementRequest(
    val coveringMaterialId: Int?,
    val coveredMaterialId: Int?,
    val conditions: MeasurementConditions?,
    val comments: String?,
    val measurementDate: Instant?
)
