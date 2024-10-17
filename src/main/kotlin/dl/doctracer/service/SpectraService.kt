package dl.doctracer.service

import dl.doctracer.dto.spectrum.SpectrumListElementResponse
import dl.doctracer.dto.spectrum.SpectrumResponse
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.model.Spectra
import dl.doctracer.repository.SpectraRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

@Service
class SpectraService(private val spectraRepository: SpectraRepository) {

    fun getAll(): List<SpectrumListElementResponse> {
        val spectra = spectraRepository.findAll()

        return spectra.map { it ->
            SpectrumListElementResponse(
                id = it.id ?: -1,
                measurementDate = it.measurementDate.toString(),
                spectrumType = it.spectrumType.name,
                sampleId = it.sample.id ?: -1,
                createdBy = it.user.username,
                createdAt = it.createdAt.toString()
            )
        }
    }

    fun getById(id: Int): SpectrumResponse {
        val spectrum = spectraRepository
            .findById(id)
            .getOrElse { throw EntityNotFoundException() }

        return SpectrumResponse(
            id = spectrum.id ?: -1,
            spectrumSamples = spectrum.spectrumSamples ?: "",
            measurementDate = spectrum.measurementDate.toString(),
            spectrumTypeName = spectrum.spectrumType.name,
            spectrumTypeId = spectrum.spectrumType.id ?: -1,
            deviceId = spectrum.device.id ?: -1,
            deviceName = spectrum.device.name,
            sampleId = spectrum.sample.id ?: -1,
            createdBy = spectrum.createdAt.toString(),
            createdAt = spectrum.createdAt.toString(),
            updatedAt = spectrum.updatedAt.toString()
        )
    }

    @Transactional
    fun save(spectra: Spectra): Spectra = spectraRepository.save(spectra)

    @Transactional
    fun deleteById(id: Int) = spectraRepository.deleteById(id)
}
