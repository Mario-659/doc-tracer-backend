package dl.doctracer.repository

import dl.doctracer.model.Measurement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MeasurementRepository : JpaRepository<Measurement, Int> {
//    @Query("SELECT m FROM Measurement m WHERE m.measurementDate BETWEEN :startDate AND :endDate")
//    fun findByMeasurementDateRange(startDate: Instant, endDate: Instant): List<Measurement>
//
//    @Query("SELECT m FROM Measurement m WHERE m.user.id = :userId")
//    fun findByUserId(userId: Int): List<Measurement>
}
