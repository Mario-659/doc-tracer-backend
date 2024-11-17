package dl.doctracer.repository

import dl.doctracer.model.File
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FileRepository : JpaRepository<File, Int> {
    fun findByMeasurementId(measurementId: Int): List<File>

//    @Query("SELECT f FROM File f WHERE f.fileType = :fileType")
//    fun findByFileType(fileType: String): List<File>
}
