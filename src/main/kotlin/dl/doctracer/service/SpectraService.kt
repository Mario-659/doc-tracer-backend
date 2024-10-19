package dl.doctracer.service

import dl.doctracer.dto.spectrum.CreateSpectrumRequest
import dl.doctracer.dto.spectrum.SpectrumListElementResponse
import dl.doctracer.dto.spectrum.SpectrumResponse
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.model.Spectra
import dl.doctracer.repository.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrElse

@Service
class SpectraService(
    private val spectraRepository: SpectraRepository,
    private val userRepository: UserRepository,
    private val sampleRepository: SampleRepository,
    private val deviceRepository: DeviceRepository,
    private val spectrumTypeRepository: SpectraTypeRepository
) {
    private val customLogger: Logger = LoggerFactory.getLogger(SpectraService::class.java)

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

    fun createSpectrum(spectrum: CreateSpectrumRequest) {
        val user = userRepository
            .findByUsername(spectrum.username)
            ?: throw IllegalArgumentException("Username ${spectrum.username} not found")

        val sample = sampleRepository
            .findById(spectrum.sampleId)
            .orElseThrow { IllegalArgumentException("Sample with id: ${spectrum.sampleId} not found") }

        val device = deviceRepository
            .findById(spectrum.deviceId)
            .orElseThrow { IllegalArgumentException("Device with id: ${spectrum.deviceId} not found") }

        val spectrumType = spectrumTypeRepository
            .findSpectraTypeByName(spectrum.spectrumType)
            ?: throw IllegalArgumentException("Device with id: ${spectrum.deviceId} not found")

        val newSpectrum = Spectra(
            spectrumSamples = spectrum.spectrumSample,
            measurementDate = spectrum.measurementDate,
            user = user,
            sample = sample,
            device = device,
            spectrumType = spectrumType
        )

        spectraRepository.save(newSpectrum)
    }
}
