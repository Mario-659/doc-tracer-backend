package dl.doctracer.controller

import dl.doctracer.model.CoveredMaterial
import dl.doctracer.service.CoveredMaterialService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/covered-materials")
class CoveredMaterialController(private val coveredMaterialService: CoveredMaterialService) {

    @GetMapping
    fun getAllCoveredMaterials(): List<CoveredMaterial> = coveredMaterialService.findAll()

    @GetMapping("/{id}")
    fun getCoveredMaterialById(@PathVariable id: Int): ResponseEntity<CoveredMaterial> {
        val coveredMaterial = coveredMaterialService.findById(id)
        return if (coveredMaterial != null) ResponseEntity.ok(coveredMaterial) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createCoveredMaterial(@RequestBody coveredMaterial: CoveredMaterial): CoveredMaterial =
        coveredMaterialService.save(coveredMaterial)

    @PutMapping("/{id}")
    fun updateCoveredMaterial(@PathVariable id: Int, @RequestBody updatedCoveredMaterial: CoveredMaterial): ResponseEntity<CoveredMaterial> {
        val existingCoveredMaterial = coveredMaterialService.findById(id)
        return if (existingCoveredMaterial != null) {
            val coveredMaterialToUpdate = existingCoveredMaterial.copy(
                name = updatedCoveredMaterial.name,
                description = updatedCoveredMaterial.description,
                deletedAt = updatedCoveredMaterial.deletedAt
            )
            ResponseEntity.ok(coveredMaterialService.save(coveredMaterialToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCoveredMaterial(@PathVariable id: Int): ResponseEntity<Void> {
        return if (coveredMaterialService.findById(id) != null) {
            coveredMaterialService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}