package dl.doctracer.service

import dl.doctracer.model.Spectra
import dl.doctracer.repository.SpectraRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpectraService(private val spectraRepository: SpectraRepository) {

    fun findAll(): List<Spectra> = spectraRepository.findAll()

    fun findById(id: Int): Spectra? = spectraRepository.findById(id).orElse(null)

    @Transactional
    fun save(spectra: Spectra): Spectra = spectraRepository.save(spectra)

    @Transactional
    fun deleteById(id: Int) = spectraRepository.deleteById(id)
}
