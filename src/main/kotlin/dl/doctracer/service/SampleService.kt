package dl.doctracer.service

import dl.doctracer.dto.sample.CreateSampleRequest
import dl.doctracer.dto.sample.SampleResponse
import dl.doctracer.dto.sample.SpectralDataMapper
import dl.doctracer.dto.sample.UpdateSampleRequest
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.model.Sample
import dl.doctracer.repository.*
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class SampleService(
    private val sampleRepository: SampleRepository,
    private val measurementRepository: MeasurementRepository,
    private val spectralDataMapper: SpectralDataMapper
) {

    fun getAllSamples(): List<SampleResponse> {
        return sampleRepository.findAll().map { mapToSampleResponse(it) }
    }

    fun getSampleById(id: Int): SampleResponse {
        val sample = sampleRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }

        return mapToSampleResponse(sample)
    }

    fun createSample(request: CreateSampleRequest): SampleResponse {
        val measurement = measurementRepository.findById(request.measurementId)
            .orElseThrow { EntityNotFoundException() }

        val newSample = Sample(
            id = null,
            measurement = measurement,
            name = request.name,
            spectralData = spectralDataMapper.map(request.spectralData),
            type = request.type,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        val savedSample = sampleRepository.save(newSample)
        return mapToSampleResponse(savedSample)
    }

    fun updateSample(id: Int, request: UpdateSampleRequest): SampleResponse {
        val existingSample = sampleRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }


        val updatedSample = existingSample.copy(
            name = request.name ?: existingSample.name,
            spectralData = request.spectralData?.let { spectralDataMapper.map(it) } ?: existingSample.spectralData,
            type = request.type ?: existingSample.type,
            updatedAt = Instant.now()
        )

        val savedSample = sampleRepository.save(updatedSample)
        return mapToSampleResponse(savedSample)
    }

    fun deleteSample(id: Int) {
        val sample = sampleRepository.findById(id)
            .orElseThrow { EntityNotFoundException() }

        sampleRepository.delete(sample)
    }

    private fun mapToSampleResponse(sample: Sample): SampleResponse {
        return SampleResponse(
            id = sample.id!!,
            measurementId = sample.measurement.id!!,
            name = sample.name,
            spectralData = spectralDataMapper.mapToResponse(sample.spectralData),
            type = sample.type,
            createdAt = sample.createdAt,
            updatedAt = sample.updatedAt
        )
    }
}

