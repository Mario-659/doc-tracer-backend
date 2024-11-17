package dl.doctracer.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "measurement_conditions")
data class MeasurementConditions(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column
    val imageMode: Int? = null,
    @Column
    val lightSource: Int? = null,
    @Column
    val ovdLED: Int? = null,
    @Column
    val phosDelay: Int? = null,
    @Column
    val spotZoom: Int? = null,
    @Column
    val iris: Int? = null,
    @Column
    val filterLink: Int? = null,
    @Column
    val fluorescenceSp: Int? = null,
    @Column
    val fluorescenceLp: Int? = null,
    @Column
    val bandpassFilter: Int? = null,
    @Column
    val diffuser: Int? = null,
    @Column
    val cameraFilter1: Int? = null,
    @Column
    val cameraFilter2: Int? = null,
    @Column
    val stageX: Int? = null,
    @Column
    val stageY: Int? = null,
    @Column
    val IR_LED: Int? = null,
    @Column
    val colorIR: Int? = null,
    @Column
    val digitalZoom: Int? = null,
    @Column
    val dimmerLevel: Int? = null,
    @Column
    val brightness: Int? = null,
    @Column
    val gamma: Int? = null,
    @Column
    val exposure: Float? = null,
    @Column
    val integration: Float? = null,
    @Column
    val gain: Int? = null,
    @Column
    val sharpness: Int? = null,
    @Column
    val focus: Int? = null,
    @Column
    val lensFocus: Int? = null,

    @Column
    val description: String? = null,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(nullable = false)
    val updatedAt: Instant = Instant.now()
)
