package dl.doctracer.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpectraRepository : JpaRepository<Spectra, Int>
