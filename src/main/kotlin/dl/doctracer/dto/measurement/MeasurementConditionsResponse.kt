package dl.doctracer.dto.measurement

data class MeasurementConditionsResponse(
    val id: Int,
    val description: String?,
    val lightSource: String?,
    val exposure: Float?,
    val gain: Int?,
    val brightness: Int?
)