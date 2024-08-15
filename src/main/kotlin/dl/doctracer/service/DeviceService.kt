package dl.doctracer.service

import dl.doctracer.model.Device
import dl.doctracer.repository.DeviceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeviceService(private val deviceRepository: DeviceRepository) {

    fun findAll(): List<Device> = deviceRepository.findAll()

    fun findById(id: Int): Device? = deviceRepository.findById(id).orElse(null)

    @Transactional
    fun save(device: Device): Device = deviceRepository.save(device)

    @Transactional
    fun deleteById(id: Int) = deviceRepository.deleteById(id)
}
