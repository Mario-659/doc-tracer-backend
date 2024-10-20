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
    fun getDeviceById(@PathVariable id: Int): ResponseEntity<Device> =
        ResponseEntity.ok(deviceService.getById(id))
}
