package dl.doctracer.repository

import dl.doctracer.model.Sample
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SampleRepository : JpaRepository<Sample, Int> {
    @Query("SELECT s FROM Sample s WHERE s.measurement.id = :measurementId")
    fun findAllByMeasurementId(@Param("measurementId") measurementId: Int): List<Sample>
}
