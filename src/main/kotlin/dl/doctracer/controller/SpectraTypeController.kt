package dl.doctracer.controller

//import dl.doctracer.model.SpectraType
//import dl.doctracer.service.SpectraTypeService
//import org.springframework.http.ResponseEntity
////import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/spectra-types")
//class SpectraTypeController(private val spectraTypeService: SpectraTypeService) {

//    @GetMapping
//    fun getAllSpectraTypes(): List<SpectraType> = spectraTypeService.findAll()

//    @GetMapping("/{id}")
//    fun getSpectraTypeById(@PathVariable id: Int): ResponseEntity<SpectraType> {
//        val spectraType = spectraTypeService.findById(id)
//        return if (spectraType != null) ResponseEntity.ok(spectraType) else ResponseEntity.notFound().build()
//    }
//
//    @PostMapping
//    fun createSpectraType(@RequestBody spectraType: SpectraType): SpectraType = spectraTypeService.save(spectraType)
//
//    @PutMapping("/{id}")
//    fun updateSpectraType(@PathVariable id: Int, @RequestBody updatedSpectraType: SpectraType): ResponseEntity<SpectraType> {
//        val existingSpectraType = spectraTypeService.findById(id)
//        return if (existingSpectraType != null) {
//            val spectraTypeToUpdate = existingSpectraType.copy(
//                name = updatedSpectraType.name,
//                description = updatedSpectraType.description,
//                deletedAt = updatedSpectraType.deletedAt
//            )
//            ResponseEntity.ok(spectraTypeService.save(spectraTypeToUpdate))
//        } else {
//            ResponseEntity.notFound().build()
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    fun deleteSpectraType(@PathVariable id: Int): ResponseEntity<Void> {
//        return if (spectraTypeService.findById(id) != null) {
//            spectraTypeService.deleteById(id)
//            ResponseEntity.noContent().build()
//        } else {
//            ResponseEntity.notFound().build()
//        }
//    }
//}
