package dl.doctracer.service

import dl.doctracer.model.SpectraType
import dl.doctracer.repository.SpectraTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpectraTypeService(private val spectraTypeRepository: SpectraTypeRepository) {

    fun findAll(): List<SpectraType> = spectraTypeRepository.findAll()

    fun findById(id: Int): SpectraType? = spectraTypeRepository.findById(id).orElse(null)

    @Transactional
    fun save(spectraType: SpectraType): SpectraType = spectraTypeRepository.save(spectraType)

    @Transactional
    fun deleteById(id: Int) = spectraTypeRepository.deleteById(id)
}
