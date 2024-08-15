package dl.doctracer.service

import dl.doctracer.model.CoveringMaterial
import dl.doctracer.repository.CoveringMaterialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CoveringMaterialService(private val coveringMaterialRepository: CoveringMaterialRepository) {

    fun findAll(): List<CoveringMaterial> = coveringMaterialRepository.findAll()

    fun findById(id: Int): CoveringMaterial? = coveringMaterialRepository.findById(id).orElse(null)

    @Transactional
    fun save(coveringMaterial: CoveringMaterial): CoveringMaterial = coveringMaterialRepository.save(coveringMaterial)

    @Transactional
    fun deleteById(id: Int) = coveringMaterialRepository.deleteById(id)
}
