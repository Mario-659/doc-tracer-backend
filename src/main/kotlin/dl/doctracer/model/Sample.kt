package dl.doctracer.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "samples")
data class Sample(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = 0,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val sampleCreationDate: Instant,

    @ManyToOne
    @JoinColumn(name = "coveredMaterialId", nullable = false)
    val coveredMaterial: CoveredMaterial,

    @ManyToOne
    @JoinColumn(name = "coveringMaterialId", nullable = false)
    val coveringMaterial: CoveringMaterial,

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    val user: User,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column
    val deletedAt: Instant? = null
)
