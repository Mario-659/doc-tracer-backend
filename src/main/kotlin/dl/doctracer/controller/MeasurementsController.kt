package dl.doctracer.controller

import dl.doctracer.dto.measurement.MeasurementRequest
import dl.doctracer.dto.measurement.MeasurementResponse
import dl.doctracer.service.MeasurementService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/measurements")
class MeasurementsController(
    private val measurementService: MeasurementService
) {

    @GetMapping
    fun getAllMeasurements(): List<MeasurementResponse> {
        return measurementService.getAllMeasurements()
    }

    @GetMapping("/{id}")
    fun getMeasurementById(@PathVariable id: Int): MeasurementResponse {
        return measurementService.getMeasurementById(id)
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
