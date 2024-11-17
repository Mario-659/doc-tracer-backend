package dl.doctracer.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "measurements")
data class Measurement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "coveringMaterialId", nullable = false)
    val coveringMaterial: CoveringMaterial,

    @ManyToOne
    @JoinColumn(name = "coveredMaterialId", nullable = false)
    val coveredMaterial: CoveredMaterial,

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "deviceId", nullable = false)
    val device: Device,

    @ManyToOne
    @JoinColumn(name = "conditionsId", nullable = false)
    val conditions: MeasurementConditions,

    @Column(columnDefinition = "TEXT")
    val comments: String? = null,

    @Column(nullable = false)
    val measurementDate: Instant,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column
    val deletedAt: Instant? = null
)
