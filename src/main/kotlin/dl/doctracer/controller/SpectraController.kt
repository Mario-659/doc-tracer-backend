package dl.doctracer.controller

import dl.doctracer.dto.spectrum.CreateSpectrumRequest
import dl.doctracer.dto.spectrum.SpectrumListElementResponse
import dl.doctracer.dto.spectrum.SpectrumResponse
import dl.doctracer.dto.spectrum.UpdateSpectrumRequest
import dl.doctracer.service.SpectraService
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties.Server.Spec
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/spectra")
class SpectraController(private val spectraService: SpectraService) {

    @GetMapping
    fun getAllSpectra(): ResponseEntity<List<SpectrumListElementResponse>> =
        ResponseEntity.ok(spectraService.getAll())

    @GetMapping("/{id}")
    fun getSpectrumById(@PathVariable id: Int): ResponseEntity<SpectrumResponse> =
        ResponseEntity.ok(spectraService.getById(id))


    @PostMapping
    fun createSpectrum(@RequestBody spectrum: CreateSpectrumRequest): ResponseEntity<Void> {
        spectraService.createSpectrum(spectrum)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}")
    fun updateSpectrum(@PathVariable id: Int, @RequestBody spectrumRequest: UpdateSpectrumRequest): ResponseEntity<SpectrumResponse> =
        ResponseEntity.ok(spectraService.updateSpectrum(id, spectrumRequest))


//    @DeleteMapping("/{id}")
//    fun deleteSpectra(@PathVariable id: Int): ResponseEntity<Void> {
//        return if (spectraService.findById(id) != null) {
//            spectraService.deleteById(id)
//            ResponseEntity.noContent().build()
//        } else {
//            ResponseEntity.notFound().build()
//        }
//    }
}
