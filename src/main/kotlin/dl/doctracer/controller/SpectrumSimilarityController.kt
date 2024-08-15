package dl.doctracer.controller

import dl.doctracer.model.SpectrumSimilarity
import dl.doctracer.service.SpectrumSimilarityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/spectrum-similarities")
class SpectrumSimilarityController(private val spectrumSimilarityService: SpectrumSimilarityService) {

    @GetMapping
    fun getAllSpectrumSimilarities(): List<SpectrumSimilarity> = spectrumSimilarityService.findAll()

    @GetMapping("/{id}")
    fun getSpectrumSimilarityById(@PathVariable id: Int): ResponseEntity<SpectrumSimilarity> {
        val spectrumSimilarity = spectrumSimilarityService.findById(id)
        return if (spectrumSimilarity != null) ResponseEntity.ok(spectrumSimilarity) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createSpectrumSimilarity(@RequestBody spectrumSimilarity: SpectrumSimilarity): SpectrumSimilarity =
        spectrumSimilarityService.save(spectrumSimilarity)

    @PutMapping("/{id}")
    fun updateSpectrumSimilarity(@PathVariable id: Int, @RequestBody updatedSpectrumSimilarity: SpectrumSimilarity): ResponseEntity<SpectrumSimilarity> {
        val existingSpectrumSimilarity = spectrumSimilarityService.findById(id)
        return if (existingSpectrumSimilarity != null) {
            val spectrumSimilarityToUpdate = existingSpectrumSimilarity.copy(
                comparisonDate = updatedSpectrumSimilarity.comparisonDate,
                similarity = updatedSpectrumSimilarity.similarity,
                baseSpectrum = updatedSpectrumSimilarity.baseSpectrum,
                comparedSpectrum = updatedSpectrumSimilarity.comparedSpectrum,
                deletedAt = updatedSpectrumSimilarity.deletedAt
            )
            ResponseEntity.ok(spectrumSimilarityService.save(spectrumSimilarityToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSpectrumSimilarity(@PathVariable id: Int): ResponseEntity<Void> {
        return if (spectrumSimilarityService.findById(id) != null) {
            spectrumSimilarityService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
