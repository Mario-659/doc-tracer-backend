package dl.doctracer.controller

import dl.doctracer.model.Sample
import dl.doctracer.service.SampleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/samples")
class SampleController(private val sampleService: SampleService) {

    @GetMapping
    fun getAllSamples(): List<Sample> = sampleService.findAll()

    @GetMapping("/{id}")
    fun getSampleById(@PathVariable id: Int): ResponseEntity<Sample> {
        val sample = sampleService.findById(id)
        return if (sample != null) ResponseEntity.ok(sample) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createSample(@RequestBody sample: Sample): Sample = sampleService.save(sample)

    @PutMapping("/{id}")
    fun updateSample(@PathVariable id: Int, @RequestBody updatedSample: Sample): ResponseEntity<Sample> {
        val existingSample = sampleService.findById(id)
        return if (existingSample != null) {
            val sampleToUpdate = existingSample.copy(
                description = updatedSample.description,
                sampleCreationDate = updatedSample.sampleCreationDate,
                coveredMaterial = updatedSample.coveredMaterial,
                coveringMaterial = updatedSample.coveringMaterial,
                user = updatedSample.user,
                deletedAt = updatedSample.deletedAt
            )
            ResponseEntity.ok(sampleService.save(sampleToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSample(@PathVariable id: Int): ResponseEntity<Void> {
        return if (sampleService.findById(id) != null) {
            sampleService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
