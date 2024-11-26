package dl.doctracer.controller

import dl.doctracer.dto.measurement.MeasurementRequest
import dl.doctracer.dto.measurement.MeasurementResponse
import dl.doctracer.dto.measurement.UpdateMeasurementRequest
import dl.doctracer.dto.sample.SampleResponse
import dl.doctracer.model.Sample
import dl.doctracer.service.MeasurementService
import dl.doctracer.service.SampleService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/measurements")
class MeasurementsController(
    private val measurementService: MeasurementService,
    private val sampleService: SampleService
) {

    @GetMapping
    @PreAuthorize("hasRole('VIEWER')")
    fun getAllMeasurements(): List<MeasurementResponse> {
        return measurementService.getAllMeasurements()
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('VIEWER')")
    fun getMeasurementById(@PathVariable id: Int): MeasurementResponse {
        return measurementService.getMeasurementById(id)
    }

    @GetMapping("/{id}/samples")
    @PreAuthorize("hasRole('VIEWER')")
    fun getMeasurementSamples(@PathVariable id: Int): List<SampleResponse> {
        return sampleService.getSamplesByMeasurementId(id)
    }

    @PostMapping
    @PreAuthorize("hasRole('EDITOR')")
    fun createMeasurement(@RequestBody measurementRequest: MeasurementRequest): MeasurementResponse {
        return measurementService.createMeasurement(measurementRequest)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    fun updateMeasurement(
        @PathVariable id: Int,
        @RequestBody updateMeasurementRequest: UpdateMeasurementRequest
    ): MeasurementResponse {
        return measurementService.updateMeasurement(id, updateMeasurementRequest)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    fun deleteMeasurement(@PathVariable id: Int) {
        measurementService.deleteMeasurement(id)
    }
}
