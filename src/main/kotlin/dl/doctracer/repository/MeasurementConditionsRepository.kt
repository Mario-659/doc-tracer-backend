package dl.doctracer.repository

import dl.doctracer.model.MeasurementConditions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MeasurementConditionsRepository : JpaRepository<MeasurementConditions, Int> {}
