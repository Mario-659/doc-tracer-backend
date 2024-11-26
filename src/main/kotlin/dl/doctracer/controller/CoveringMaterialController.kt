package dl.doctracer.controller

import dl.doctracer.model.CoveringMaterial
import dl.doctracer.service.CoveringMaterialService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/covering-materials")
class CoveringMaterialController(private val coveringMaterialService: CoveringMaterialService) {

    @GetMapping
    @PreAuthorize("hasRole('VIEWER')")
    fun getAllCoveringMaterials(): List<CoveringMaterial> = coveringMaterialService.findAll()

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('VIEWER')")
    fun getCoveringMaterialById(@PathVariable id: Int): ResponseEntity<CoveringMaterial> {
        val coveringMaterial = coveringMaterialService.findById(id)
        return if (coveringMaterial != null) ResponseEntity.ok(coveringMaterial) else ResponseEntity.notFound().build()
    }

    @PostMapping
    @PreAuthorize("hasRole('EDITOR')")
    fun createCoveringMaterial(@RequestBody coveringMaterial: CoveringMaterial): CoveringMaterial =
        coveringMaterialService.save(coveringMaterial)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    fun updateCoveringMaterial(@PathVariable id: Int, @RequestBody updatedCoveringMaterial: CoveringMaterial): ResponseEntity<CoveringMaterial> {
        val existingCoveringMaterial = coveringMaterialService.findById(id)
        return if (existingCoveringMaterial != null) {
            val coveringMaterialToUpdate = existingCoveringMaterial.copy(
                name = updatedCoveringMaterial.name,
                description = updatedCoveringMaterial.description,
                manufacturer = updatedCoveringMaterial.manufacturer,
                color = updatedCoveringMaterial.color,
                deletedAt = updatedCoveringMaterial.deletedAt
            )
            ResponseEntity.ok(coveringMaterialService.save(coveringMaterialToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EDITOR')")
    fun deleteCoveringMaterial(@PathVariable id: Int): ResponseEntity<Void> {
        return if (coveringMaterialService.findById(id) != null) {
            coveringMaterialService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
