package dl.doctracer.repository

import dl.doctracer.model.SpectraType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpectraTypeRepository : JpaRepository<SpectraType, Int>{
    fun findSpectraTypeByName(spectraName: String): SpectraType?
}
