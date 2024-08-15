package dl.doctracer.service

import dl.doctracer.model.CoveredMaterial
import dl.doctracer.repository.CoveredMaterialRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CoveredMaterialService(private val coveredMaterialRepository: CoveredMaterialRepository) {

    fun findAll(): List<CoveredMaterial> = coveredMaterialRepository.findAll()

    fun findById(id: Int): CoveredMaterial? = coveredMaterialRepository.findById(id).orElse(null)

    @Transactional
    fun save(coveredMaterial: CoveredMaterial): CoveredMaterial = coveredMaterialRepository.save(coveredMaterial)

    @Transactional
    fun deleteById(id: Int) = coveredMaterialRepository.deleteById(id)
}
