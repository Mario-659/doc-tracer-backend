package dl.doctracer.model

import jakarta.persistence.*
import org.hibernate.annotations.ColumnTransformer
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "samples")
data class Sample(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "measurementId", nullable = false)
    val measurement: Measurement,

    @Column(nullable = false)
    val name: String,

    @ColumnTransformer(write = "?::jsonb")
    @Column(columnDefinition = "jsonb", nullable = false)
    val spectralData: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: SpectralType,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column
    val deletedAt: Instant? = null
)
