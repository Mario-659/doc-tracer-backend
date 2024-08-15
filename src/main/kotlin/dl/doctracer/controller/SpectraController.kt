package dl.doctracer.controller

import dl.doctracer.model.Spectra
import dl.doctracer.service.SpectraService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/spectra")
class SpectraController(private val spectraService: SpectraService) {

    @GetMapping
    fun getAllSpectra(): List<Spectra> = spectraService.findAll()

    @GetMapping("/{id}")
    fun getSpectraById(@PathVariable id: Int): ResponseEntity<Spectra> {
        val spectra = spectraService.findById(id)
        return if (spectra != null) ResponseEntity.ok(spectra) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createSpectra(@RequestBody spectra: Spectra): Spectra = spectraService.save(spectra)

    @PutMapping("/{id}")
    fun updateSpectra(@PathVariable id: Int, @RequestBody updatedSpectra: Spectra): ResponseEntity<Spectra> {
        val existingSpectra = spectraService.findById(id)
        return if (existingSpectra != null) {
            val spectraToUpdate = existingSpectra.copy(
                spectrumSamples = updatedSpectra.spectrumSamples,
                measurementDate = updatedSpectra.measurementDate,
                spectrumType = updatedSpectra.spectrumType,
                device = updatedSpectra.device,
                sample = updatedSpectra.sample,
                user = updatedSpectra.user,
                deletedAt = updatedSpectra.deletedAt
            )
            ResponseEntity.ok(spectraService.save(spectraToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteSpectra(@PathVariable id: Int): ResponseEntity<Void> {
        return if (spectraService.findById(id) != null) {
            spectraService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
