package dl.doctracer.model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

//@Entity
//@Table(name = "files")
//data class File(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Int? = null,
//
//    @ManyToOne
//    @JoinColumn(name = "measurementId", nullable = false)
//    val measurement: Measurement,
//
//    @Column(nullable = false)
//    val fileName: String,
//
//    @Column(nullable = false)
//    val fileType: String,
//
//    @Lob
//    @Column(nullable = false)
//    val content: ByteArray,
//
//    @ColumnTransformer(write = "?::jsonb")
//    @Column(columnDefinition = "jsonb", nullable = false)
//    val metadata: String,
//
//    @CreationTimestamp
//    @Column(nullable = false, updatable = false)
//    val createdAt: Instant = Instant.now(),
//
//    @UpdateTimestamp
//    @Column(nullable = false)
//    val updatedAt: Instant = Instant.now(),
//
//    @Column
//    val deletedAt: Instant? = null
//)
