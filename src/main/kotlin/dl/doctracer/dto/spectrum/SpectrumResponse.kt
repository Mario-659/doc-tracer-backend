package dl.doctracer.dto.spectrum

class SpectrumResponse (
    val id: Int,
    val spectrumSamples: String,
    val measurementDate: String,
    val spectrumTypeName: String,
    val spectrumTypeId: Int,
    val deviceName: String,
    val deviceId: Int,
    val sampleId: Int,
    val createdBy: String,
    val createdAt: String,
    val updatedAt: String
)