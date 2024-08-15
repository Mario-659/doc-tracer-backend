package dl.doctracer.repository

import dl.doctracer.model.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository : JpaRepository<Device, Int>
