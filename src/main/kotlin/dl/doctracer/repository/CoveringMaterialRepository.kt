package dl.doctracer.repository

import dl.doctracer.model.CoveringMaterial
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CoveringMaterialRepository : JpaRepository<CoveringMaterial, Int>
