package dl.doctracer.controller

import dl.doctracer.dto.measurement.MeasurementRequest
import dl.doctracer.dto.measurement.MeasurementResponse
import dl.doctracer.dto.sample.SampleResponse
import dl.doctracer.model.Sample
import dl.doctracer.service.MeasurementService
import dl.doctracer.service.SampleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/measurements")
class MeasurementsController(
    private val measurementService: MeasurementService,
    private val sampleService: SampleService
) {

    @GetMapping
    fun getAllMeasurements(): List<MeasurementResponse> {
        return measurementService.getAllMeasurements()
    }

    @GetMapping("/{id}")
    fun getMeasurementById(@PathVariable id: Int): MeasurementResponse {
        return measurementService.getMeasurementById(id)
    }

    @GetMapping("/{id}/samples")
    fun getMeasurementSamples(@PathVariable id: Int): List<SampleResponse> {
        return sampleService.getSamplesByMeasurementId(id)
    }

    @PostMapping
    fun createMeasurement(@RequestBody measurementRequest: MeasurementRequest): MeasurementResponse {
        return measurementService.createMeasurement(measurementRequest)
    }

    @PutMapping("/{id}")
    fun updateMeasurement(
        @PathVariable id: Int,
        @RequestBody measurementRequest: MeasurementRequest
    ): MeasurementResponse {
        return measurementService.updateMeasurement(id, measurementRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteMeasurement(@PathVariable id: Int) {
        measurementService.deleteMeasurement(id)
    }
}
