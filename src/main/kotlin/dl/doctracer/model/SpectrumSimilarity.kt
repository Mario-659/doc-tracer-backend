package dl.doctracer.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(
    name = "spectrum_similarities",
    uniqueConstraints = [UniqueConstraint(columnNames = ["baseSpectrumId", "comparedSpectrumId"])]
)
data class SpectrumSimilarity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false)
    val comparisonDate: Instant,

    @Column(nullable = false)
    val similarity: Double,

    @ManyToOne
    @JoinColumn(name = "baseSpectrumId", nullable = false)
    val baseSpectrum: Spectra,

    @ManyToOne
    @JoinColumn(name = "comparedSpectrumId", nullable = false)
    val comparedSpectrum: Spectra,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: Instant = Instant.now(),

    @Column
    val deletedAt: Instant? = null
)
