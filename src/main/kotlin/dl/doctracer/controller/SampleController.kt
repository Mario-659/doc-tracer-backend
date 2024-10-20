package dl.doctracer.controller

import dl.doctracer.dto.sample.SampleResponse
import dl.doctracer.model.Sample
import dl.doctracer.service.SampleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/samples")
class SampleController(private val sampleService: SampleService) {

    @GetMapping
    fun getAllSamples(): ResponseEntity<List<SampleResponse>> =
        ResponseEntity.ok(sampleService.getAll())

    @GetMapping("/{id}")
    fun getSampleById(@PathVariable id: Int): ResponseEntity<SampleResponse> =
        ResponseEntity.ok(sampleService.getById(id))

    @PostMapping
    fun createSample(@RequestBody sample: Sample): Sample = TODO("not implemented")

    @PutMapping("/{id}")
    fun updateSample(@PathVariable id: Int, @RequestBody updatedSample: Sample): ResponseEntity<Sample> {
        TODO("not implemented")
    }

    @DeleteMapping("/{id}")
    fun deleteSample(@PathVariable id: Int): ResponseEntity<Void> {
        TODO("not implemented")
    }
}
