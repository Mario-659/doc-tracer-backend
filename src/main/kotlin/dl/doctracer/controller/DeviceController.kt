package dl.doctracer.controller

import dl.doctracer.model.Device
import dl.doctracer.service.DeviceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/devices")
class DeviceController(private val deviceService: DeviceService) {

    @GetMapping
    fun getAllDevices(): List<Device> = deviceService.findAll()

    @GetMapping("/{id}")
    fun getDeviceById(@PathVariable id: Int): ResponseEntity<Device> {
        val device = deviceService.findById(id)
        return if (device != null) ResponseEntity.ok(device) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createDevice(@RequestBody device: Device): Device = deviceService.save(device)

    @PutMapping("/{id}")
    fun updateDevice(@PathVariable id: Int, @RequestBody updatedDevice: Device): ResponseEntity<Device> {
        val existingDevice = deviceService.findById(id)
        return if (existingDevice != null) {
            val deviceToUpdate = existingDevice.copy(
                name = updatedDevice.name,
                description = updatedDevice.description,
                manufacturer = updatedDevice.manufacturer,
                deletedAt = updatedDevice.deletedAt
            )
            ResponseEntity.ok(deviceService.save(deviceToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDevice(@PathVariable id: Int): ResponseEntity<Void> {
        return if (deviceService.findById(id) != null) {
            deviceService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
