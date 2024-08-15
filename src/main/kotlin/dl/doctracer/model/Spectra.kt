package dl.doctracer.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "spectra")
data class Spectra(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(columnDefinition = "json")
    val spectrumSamples: String? = null,

    @Column(nullable = false)
    val measurementDate: Instant,

    @ManyToOne
    @JoinColumn(name = "spectrumTypeId", nullable = false)
    val spectrumType: SpectraType,

    @ManyToOne
    @JoinColumn(name = "deviceId", nullable = false)
    val device: Device,

    @ManyToOne
    @JoinColumn(name = "sampleId", nullable = false)
    val sample: Sample,

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
