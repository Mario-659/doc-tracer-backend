package dl.doctracer.repository

import dl.doctracer.model.CoveredMaterial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CoveredMaterialRepository : JpaRepository<CoveredMaterial, Int>
