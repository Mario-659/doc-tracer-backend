package dl.doctracer.dto.spectrum

import java.time.Instant

class UpdateSpectrumRequest (
    val spectrumSample: String?,
    val spectrumType: String?,
    val measurementDate: Instant?,
    val username: String?,
    val deviceId: Int?,
    val sampleId: Int?
)
