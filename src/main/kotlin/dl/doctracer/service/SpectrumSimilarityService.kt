package dl.doctracer.service

import dl.doctracer.model.SpectrumSimilarity
import dl.doctracer.repository.SpectrumSimilarityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpectrumSimilarityService(private val spectrumSimilarityRepository: SpectrumSimilarityRepository) {

    fun findAll(): List<SpectrumSimilarity> = spectrumSimilarityRepository.findAll()

    fun findById(id: Int): SpectrumSimilarity? = spectrumSimilarityRepository.findById(id).orElse(null)

    @Transactional
    fun save(spectrumSimilarity: SpectrumSimilarity): SpectrumSimilarity = spectrumSimilarityRepository.save(spectrumSimilarity)

    @Transactional
    fun deleteById(id: Int) = spectrumSimilarityRepository.deleteById(id)
}
