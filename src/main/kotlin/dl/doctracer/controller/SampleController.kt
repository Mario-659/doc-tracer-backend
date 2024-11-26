package dl.doctracer.controller

import dl.doctracer.dto.sample.CreateSampleRequest
import dl.doctracer.dto.sample.SampleResponse
import dl.doctracer.dto.sample.UpdateSampleRequest
import dl.doctracer.service.SampleService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/samples")
class SampleController(
    private val sampleService: SampleService
) {

    @GetMapping
    @PreAuthorize("hasRole('VIEWER')")
    fun getAllSamples(): ResponseEntity<List<SampleResponse>> {
        val samples = sampleService.getAllSamples()
        return ResponseEntity.ok(samples)
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('VIEWER')")
    fun getSampleById(@PathVariable id: Int): ResponseEntity<SampleResponse> {
        val sample = sampleService.getSampleById(id)
        return ResponseEntity.ok(sample)
    }

    @PostMapping
    @PreAuthorize("hasRole('EDITOR')")
    fun createSample(@Valid @RequestBody request: CreateSampleRequest): ResponseEntity<SampleResponse> {
        val createdSample = sampleService.createSample(request)
        return ResponseEntity.ok(createdSample)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    fun updateSample(
        @PathVariable id: Int,
        @Valid @RequestBody request: UpdateSampleRequest
    ): ResponseEntity<SampleResponse> {
        val updatedSample = sampleService.updateSample(id, request)
        return ResponseEntity.ok(updatedSample)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    fun deleteSample(@PathVariable id: Int): ResponseEntity<Void> {
        sampleService.deleteSample(id)
        return ResponseEntity.noContent().build()
    }

//    @PostMapping("/{id}/spectral-data")
//    fun updateSpectralData(
//        @PathVariable id: Int,
//        @Valid @RequestBody spectralData: SpectralData
//    ): ResponseEntity<SampleResponse> {
//        val updatedSample = sampleService.updateSample(id, spectralData)
//        return ResponseEntity.ok(updatedSample)
//    }
}

