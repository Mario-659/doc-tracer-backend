package dl.doctracer.service

import dl.doctracer.dto.sample.SampleResponse
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.model.Sample
import dl.doctracer.repository.CoveredMaterialRepository
import dl.doctracer.repository.CoveringMaterialRepository
import dl.doctracer.repository.SampleRepository
import dl.doctracer.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class SampleService(
    private val sampleRepository: SampleRepository,
    private val userRepository: UserRepository,
    private val coveredMaterialRepository: CoveredMaterialRepository,
    private val coveringMaterialRepository: CoveringMaterialRepository
) {
    fun getAll(): List<SampleResponse> {
        val samples = sampleRepository.findAll()

        return samples.map { it -> mapToResponse(it)}
    }

    fun getById(id: Int): SampleResponse {
        val sample = sampleRepository
            .findById(id)
            .orElseThrow { throw EntityNotFoundException() }

        return mapToResponse(sample)
    }

    @Transactional
    fun deleteById(id: Int) = sampleRepository.deleteById(id)

//    fun createSample(sampleRequest: CreateSampleRequest) {
//        val user = userRepository
//            .findByUsername(sampleRequest.username)
//            ?: throw IllegalArgumentException("Username ${sampleRequest.username} not found")
//
//        val coveredMaterial = coveredMaterialRepository
//            .findById(sampleRequest.coveredMaterialId)
//            .orElseThrow { IllegalArgumentException("Covered material with id: ${sampleRequest.coveredMaterialId} not found") }
//
//        val coveringMaterial = coveringMaterialRepository
//            .findById(sampleRequest.coveringMaterialId)
//            .orElseThrow { IllegalArgumentException("Covering material with id: ${sampleRequest.coveringMaterialId} not found") }
//
//        val newSample = Sample(
//            description = sampleRequest.description,
//            sampleCreationDate = sampleRequest.sampleCreationDate,
//            coveredMaterial = coveredMaterial,
//            coveringMaterial = coveringMaterial,
//            user = user
//        )
//
//        sampleRepository.save(newSample)
//    }

//    fun updateSample(id: Int, updateSampleRequest: UpdateSampleRequest): SampleResponse {
//        val existingSample = sampleRepository
//            .findById(id)
//            .orElseThrow { IllegalArgumentException("Sample with id: $id not found") }
//
//        val updatedUser = updateSampleRequest.username?.let { username ->
//            userRepository.findByUsername(username)
//                ?: throw IllegalArgumentException("Username $username not found")
//        } ?: existingSample.user
//
//        val updatedCoveredMaterial = updateSampleRequest.coveredMaterialId?.let { coveredMaterialId ->
//            coveredMaterialRepository.findById(coveredMaterialId)
//                .orElseThrow { IllegalArgumentException("Covered material with id: $coveredMaterialId not found") }
//        } ?: existingSample.coveredMaterial
//
//        val updatedCoveringMaterial = updateSampleRequest.coveringMaterialId?.let { coveringMaterialId ->
//            coveringMaterialRepository.findById(coveringMaterialId)
//                .orElseThrow { IllegalArgumentException("Covering material with id: $coveringMaterialId not found") }
//        } ?: existingSample.coveringMaterial
//
//        val updatedSample = existingSample.copy(
//            description = updateSampleRequest.description ?: existingSample.description,
//            sampleCreationDate = updateSampleRequest.sampleCreationDate ?: existingSample.sampleCreationDate,
//            user = updatedUser,
//            coveredMaterial = updatedCoveredMaterial,
//            coveringMaterial = updatedCoveringMaterial
//        )
//
//        val savedSample = sampleRepository.save(updatedSample)
//        return mapToResponse(savedSample)
//    }

    private fun mapToResponse(sample: Sample): SampleResponse =
        SampleResponse(
            id = sample.id ?: -1,
            description = sample.description,
            sampleCreationDate = sample.sampleCreationDate,
            coveredMaterialName = sample.coveredMaterial.name,
            coveredMaterialId = sample.coveredMaterial.id ?: -1,
            coveringMaterialName = sample.coveringMaterial.name,
            coveringMaterialId = sample.coveringMaterial.id ?: -1,
            createdBy = sample.user.username,
            createdAt = sample.createdAt,
            updatedAt = sample.updatedAt
        )
}

