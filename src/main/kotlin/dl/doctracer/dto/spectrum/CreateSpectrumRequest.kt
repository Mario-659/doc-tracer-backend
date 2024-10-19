package dl.doctracer.dto.spectrum

import jakarta.validation.constraints.NotBlank
import java.time.Instant

class CreateSpectrumRequest (
    @field:NotBlank()
    val spectrumSample: String,
    @field:NotBlank()
    val spectrumType: String,
    @field:NotBlank()
    val measurementDate: Instant,
    @field:NotBlank()
    val username: String,
    @field:NotBlank()
    val deviceId: Int,
    @field:NotBlank()
    val sampleId: Int
)
