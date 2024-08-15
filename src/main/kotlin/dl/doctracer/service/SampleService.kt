package dl.doctracer.service

import dl.doctracer.model.Sample
import dl.doctracer.repository.SampleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleService(private val sampleRepository: SampleRepository) {

    fun findAll(): List<Sample> = sampleRepository.findAll()

    fun findById(id: Int): Sample? = sampleRepository.findById(id).orElse(null)

    @Transactional
    fun save(sample: Sample): Sample = sampleRepository.save(sample)

    @Transactional
    fun deleteById(id: Int) = sampleRepository.deleteById(id)
}
