package dl.doctracer.service

import dl.doctracer.dto.measurement.MeasurementConditionsResponse
import dl.doctracer.dto.measurement.MeasurementRequest
import dl.doctracer.dto.measurement.MeasurementResponse
import dl.doctracer.dto.measurement.UpdateMeasurementRequest
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.model.Measurement
import dl.doctracer.model.MeasurementConditions
import dl.doctracer.repository.DeviceRepository
import dl.doctracer.repository.MeasurementConditionsRepository
import dl.doctracer.repository.MeasurementRepository
import dl.doctracer.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class MeasurementService(
    private val measurementRepository: MeasurementRepository,
    private val userRepository: UserRepository,
    private val deviceRepository: DeviceRepository,
    private val coveringMaterialService: CoveringMaterialService,
    private val coveredMaterialService: CoveredMaterialService,
    private val measurementConditionsRepository: MeasurementConditionsRepository
) {

    fun getAllMeasurements(): List<MeasurementResponse> {
        return measurementRepository.findAll().map { measurement ->
            toMeasurementResponse(measurement)
        }
    }

    fun getMeasurementById(id: Int): MeasurementResponse {
        val measurement = measurementRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }
        return toMeasurementResponse(measurement)
    }

    fun createMeasurement(request: MeasurementRequest): MeasurementResponse {
        val coveringMaterial = coveringMaterialService.findById(request.coveringMaterialId)
            ?: throw EntityNotFoundException()

        val coveredMaterial = coveredMaterialService.findById(request.coveredMaterialId)
            ?: throw EntityNotFoundException()

        val user = userRepository.findById(request.userId)
            .orElseThrow { EntityNotFoundException() }

        val device = deviceRepository.findById(request.deviceId)
            .orElseThrow { EntityNotFoundException() }

        val conditions = measurementConditionsRepository.findById(request.conditionsId)
            .orElseThrow { EntityNotFoundException() }

        val measurement = Measurement(
            coveringMaterial = coveringMaterial,
            coveredMaterial = coveredMaterial,
            user = user,
            device = device,
            conditions = conditions,
            comments = request.comments,
            measurementDate = request.measurementDate
        )

        return toMeasurementResponse(measurementRepository.save(measurement))
    }

    fun updateMeasurement(id: Int, request: UpdateMeasurementRequest): MeasurementResponse {
        val existingMeasurement = measurementRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }

        val coveringMaterial = request.coveringMaterialId?.let { coveringMaterialService.findById(it) }
        val coveredMaterial = request.coveredMaterialId?.let { coveredMaterialService.findById(it) }

        val updatedConditions = request.conditions?.let {
            existingMeasurement.conditions.copy(
                description = it.description ?: existingMeasurement.conditions.description,
                lightSource = it.lightSource ?: existingMeasurement.conditions.lightSource,
                exposure = it.exposure ?: existingMeasurement.conditions.exposure,
                gain = it.gain ?: existingMeasurement.conditions.gain,
                brightness = it.brightness ?: existingMeasurement.conditions.brightness,
            )
        }

        val updatedMeasurement = existingMeasurement.copy(
            coveringMaterial = coveringMaterial ?: existingMeasurement.coveringMaterial,
            coveredMaterial = coveredMaterial ?: existingMeasurement.coveredMaterial,
            conditions = updatedConditions ?: existingMeasurement.conditions,
            comments = request.comments ?: existingMeasurement.comments,
            measurementDate = request.measurementDate ?: existingMeasurement.measurementDate,
            updatedAt = Instant.now()
        )

        return toMeasurementResponse(measurementRepository.save(updatedMeasurement))
    }

    fun deleteMeasurement(id: Int) {
        val measurement = measurementRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }
        measurementRepository.delete(measurement)
    }

    private fun toMeasurementResponse(measurement: Measurement): MeasurementResponse {
        return MeasurementResponse(
            id = measurement.id!!,
            coveringMaterial = measurement.coveringMaterial.name,
            coveredMaterial = measurement.coveredMaterial.name,
            user = measurement.user.username,
            device = measurement.device.name,
            conditions = toMeasurementConditionsResponse(measurement.conditions),
            comments = measurement.comments,
            measurementDate = measurement.measurementDate,
            createdAt = measurement.createdAt,
            updatedAt = measurement.updatedAt
        )
    }

    private fun toMeasurementConditionsResponse(conditions: MeasurementConditions): MeasurementConditionsResponse {
        return MeasurementConditionsResponse(
            id = conditions.id!!,
            description = conditions.description,
            lightSource = conditions.lightSource.toString(),
            exposure = conditions.exposure,
            gain = conditions.gain,
            brightness = conditions.brightness
        )
    }
}
