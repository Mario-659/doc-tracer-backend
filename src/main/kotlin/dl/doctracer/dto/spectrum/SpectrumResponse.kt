package dl.doctracer.dto.spectrum

import java.time.Instant

class SpectrumResponse (
    val id: Int,
    val spectrumSamples: String,
    val measurementDate: Instant,
    val spectrumTypeName: String,
    val spectrumTypeId: Int,
    val deviceName: String,
    val deviceId: Int,
    val sampleId: Int,
    val createdBy: String,
    val createdAt: Instant,
    val updatedAt: Instant
)