package dl.doctracer.configuration

import dl.doctracer.dto.auth.RegisterRequest
import dl.doctracer.model.*
import dl.doctracer.repository.*
import dl.doctracer.service.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Configuration
@Profile("dev")
class DatabaseSeeder {
    private val logger: Logger = LoggerFactory.getLogger(DatabaseSeeder::class.java)

    @Bean
    fun seedDatabase(
        userAuthService: UserAuthService,
        userRepository: UserRepository,
        deviceService: DeviceService,
        sampleRepository: SampleRepository,
        coveredMaterialService: CoveredMaterialService,
        coveringMaterialService: CoveringMaterialService,
        roleRepository: RoleRepository,
        measurementConditionsRepository: MeasurementConditionsRepository,
        measurementRepository: MeasurementRepository,
        fileRepository: FileRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            logger.info("Initializing database with mock data")

            // initializing roles,
            // TODO consider moving that to different class, see https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-spring-application.html#boot-features-application-events-and-listeners
            val roles = listOf("ADMIN", "EDITOR", "VIEWER")
            roles.forEach { roleName ->
                roleRepository.findByRoleName(roleName) ?: roleRepository.save(Role(roleName = roleName))
            }

            val adminRole = roleRepository.findByRoleName("ADMIN") ?: throw Exception()
            val editorRole = roleRepository.findByRoleName("EDITOR") ?: throw Exception()
            val viewerRole = roleRepository.findByRoleName("VIEWER") ?: throw Exception()

            val registerRequests = listOf(
                RegisterRequest("admin", "password1", "admin1@gmail.com", "Admin", "Administrator"),
                RegisterRequest("user1", "password1", "user1@gmail.com", "User", "One"),
                RegisterRequest("user2", "password2", "user2@gmail.com", "User", "Two")
            )
            val users = registerRequests
                .map { userAuthService.register(it) }
                .map { registeredUser -> registeredUser.copy(isActive = true) }
                .map { activeUser ->
                    if (activeUser.username == "admin") {
                        val updatedRoles = activeUser.roles + adminRole + editorRole + viewerRole
                        activeUser.copy(roles = updatedRoles)
                    } else activeUser
                }
                .map { updatedUser -> userRepository.save(updatedUser) }

            var devices = listOf(
                Device(
                    null,
                    "VSC800-HS",
                    "Designed to meet the requirements of immigration authorities, government agencies, and forensic science laboratories",
                    "Foster + Freeman"
                ),
                Device(null, "LCMS-2050", "High-Performance Liquid Chromatograph Mass Spectrometer", "Shimadzu"),
                Device(null, "NMR-500", "Nuclear Magnetic Resonance Spectrometer", "Bruker"),
                Device(
                    null,
                    "Cary 60 UV-Vis",
                    "The Cary 60 UV-Vis spectrophotometer is a double-beam instrument with a powerful xenon lamp that flashes 80 times per second. The xenon lamp only illuminates the sample when data is acquired, protecting sensitive samples from photodegradation, and reducing power consumption. The highly focused beam is ideal for measuring small sample volumes accurately and reproducibly.",
                    "Agilent"
                ),
                Device(
                    null,
                    "inVia",
                    "The ultimate research-grade confocal Raman microscope delivers outstanding performance and the best data in the shortest time",
                    "RENISHAW"
                )
            )
            devices = devices.map { deviceService.save(it) }

            var coveredMaterials = listOf(
                CoveredMaterial(null, "Standard White Paper", "Common office printing paper"),
                CoveredMaterial(null, "Glossy Photo Paper", "High-gloss photo printing paper"),
                CoveredMaterial(null, "Cardstock", "Thicker paper used for business cards and invitations"),
                CoveredMaterial(null, "Recycled Paper", "Eco-friendly paper made from recycled materials"),
                CoveredMaterial(null, "Tracing Paper", "Translucent paper used for design and drafting"),
                CoveredMaterial(null, "Laminated Paper", "Paper coated with a thin layer of plastic for durability"),
                CoveredMaterial(null, "Newsprint", "Thin paper commonly used for newspapers"),
                CoveredMaterial(null, "Art Paper", "High-quality paper for professional artwork and illustrations")
            )
            coveredMaterials = coveredMaterials.map { coveredMaterialService.save(it) }


            var coveringMaterials = listOf(
                CoveringMaterial(null, "Covering A", "Matte black toner", "HP", "Black"),
                CoveringMaterial(null, "Covering B", "Glossy red ink", "Pilot", "Red"),
                CoveringMaterial(null, "Covering C", "Waterproof blue ink", "Pentel", "Blue"),
                CoveringMaterial(null, "Covering D", "Vibrant green ink", "Lamy", "Green"),
                CoveringMaterial(null, "Covering E", "Quick-dry black ink", "Uni-ball", "Black")
            )
            coveringMaterials = coveringMaterials.map { coveringMaterialService.save(it) }

            val measurementConditions = listOf(
                MeasurementConditions(
                    null,
                    imageMode = 1,
                    lightSource = 1,
                    description = "Default measurement settings"
                ),
                MeasurementConditions(
                    null,
                    imageMode = 2,
                    lightSource = 1,
                    description = "High-resolution mode with standard light source"
                ),
                MeasurementConditions(
                    null,
                    imageMode = 3,
                    lightSource = 2,
                    description = "Infrared imaging with alternate light source"
                ),
                MeasurementConditions(
                    null,
                    imageMode = 4,
                    lightSource = 3,
                    description = "UV imaging for fluorescence analysis"
                ),
                MeasurementConditions(
                    null,
                    imageMode = 1,
                    lightSource = 4,
                    description = "Polarized light with enhanced contrast"
                ),
                MeasurementConditions(
                    null,
                    imageMode = 5,
                    lightSource = 1,
                    description = "Low-light mode for sensitive samples"
                ),
                MeasurementConditions(
                    null,
                    imageMode = 2,
                    lightSource = 3,
                    description = "Extended wavelength range for material differentiation"
                ),
                MeasurementConditions(
                    null,
                    imageMode = 3,
                    lightSource = 4,
                    description = "Custom settings for experimental purposes"
                )
            ).map { measurementConditionsRepository.save(it) }

            val specificDates = listOf(
                LocalDateTime.of(2024, 10, 5, 10, 30).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2024, 10, 15, 14, 45).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2024, 11, 1, 9, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2024, 11, 20, 16, 20).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2024, 12, 5, 11, 15).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2024, 12, 10, 13, 30).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2024, 12, 15, 15, 0).toInstant(ZoneOffset.UTC),
                LocalDateTime.of(2024, 12, 20, 18, 10).toInstant(ZoneOffset.UTC)
            )

            val measurements = listOf(
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[0],
                    coveredMaterial = coveredMaterials[0],
                    user = users[0],
                    device = devices[0],
                    conditions = measurementConditions[0],
                    measurementDate = specificDates[0],
                    comments = "Test measurement"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[1],
                    coveredMaterial = coveredMaterials[1],
                    user = users[1],
                    device = devices[1],
                    conditions = measurementConditions[1],
                    measurementDate = specificDates[1],
                    comments = "Another test measurement"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[2],
                    coveredMaterial = coveredMaterials[2],
                    user = users[2],
                    device = devices[2],
                    conditions = measurementConditions[2],
                    measurementDate = specificDates[2],
                    comments = "Testing green ink on cardstock"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[3],
                    coveredMaterial = coveredMaterials[3],
                    user = users[0],
                    device = devices[3],
                    conditions = measurementConditions[3],
                    measurementDate = specificDates[3],
                    comments = "Analyzing blue ink on recycled paper"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[4],
                    coveredMaterial = coveredMaterials[4],
                    user = users[1],
                    device = devices[4],
                    conditions = measurementConditions[4],
                    measurementDate = specificDates[4],
                    comments = "Evaluating black ink on tracing paper"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[0],
                    coveredMaterial = coveredMaterials[5],
                    user = users[0],
                    device = devices[0],
                    conditions = measurementConditions[5],
                    measurementDate = specificDates[5],
                    comments = "Black toner on laminated paper"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[1],
                    coveredMaterial = coveredMaterials[6],
                    user = users[1],
                    device = devices[1],
                    conditions = measurementConditions[6],
                    measurementDate = specificDates[6],
                    comments = "Red ink on newsprint"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[3],
                    coveredMaterial = coveredMaterials[7],
                    user = users[2],
                    device = devices[2],
                    conditions = measurementConditions[7],
                    measurementDate = specificDates[7],
                    comments = "Green ink on art paper"
                )
            ).map { measurementRepository.save(it) }

            val spectralDataList = listOf(
                spectralDataOne, spectralDataTwo, spectralDataThree, spectralDataFour, spectralDataFive
            )

            val samplesA = spectralDataList.mapIndexed { index, json ->
                Sample(
                    id = null,
                    measurement = measurements[0],
                    name = "Sample ${'A' + index} - M1",
                    spectralData = json,
                    type = SpectralType.ABSORPTION,
                    createdAt = specificDates[index % specificDates.size],
                    updatedAt = specificDates[index % specificDates.size],
                    deletedAt = null
                )
            }
            samplesA.forEach { sampleRepository.save(it) }

            val samplesB = spectralDataList.mapIndexed { index, json ->
                Sample(
                    id = null,
                    measurement = measurements[1],
                    name = "Sample ${'A' + index} - M2",
                    spectralData = json,
                    type = SpectralType.REFLECTANCE,
                    createdAt = specificDates[index % specificDates.size],
                    updatedAt = specificDates[index % specificDates.size],
                    deletedAt = null
                )
            }
            samplesB.forEach { sampleRepository.save(it) }


            val samples3 = spectralDataList.mapIndexed { index, json ->
                Sample(
                    id = null,
                    measurement = measurements[2],
                    name = "Sample ${'A' + index} - M3",
                    spectralData = json,
                    type = SpectralType.FLUORESCENCE,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    deletedAt = null
                )
            }
            samples3.forEach { sampleRepository.save(it) }

            sampleRepository.save(
                Sample(
                    id = null,
                    measurement = measurements[3],
                    name = "Sample Fluorescence",
                    spectralData = spectralDataList[0],
                    type = SpectralType.FLUORESCENCE,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    deletedAt = null
                )
            )

            sampleRepository.save(
                Sample(
                    id = null,
                    measurement = measurements[4],
                    name = "Sample Average",
                    spectralData = spectralDataList[1],
                    type = SpectralType.AVERAGE,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    deletedAt = null
                )
            )

            sampleRepository.save(
                Sample(
                    id = null,
                    measurement = measurements[5],
                    name = "Sample Differential - M6",
                    spectralData = spectralDataList[2],
                    type = SpectralType.DIFFERENTIAL,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    deletedAt = null
                )
            )


            sampleRepository.save(
                Sample(
                    id = null,
                    measurement = measurements[6],
                    name = "Sample A Normalised - M7",
                    spectralData = spectralDataList[4],
                    type = SpectralType.NORMALISED,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    deletedAt = null
                )
            )


            sampleRepository.save(
                Sample(
                    id = null,
                    measurement = measurements[7],
                    name = "Sample A - M8",
                    spectralData = spectralDataList[3],
                    type = SpectralType.TRANSMITTANCE,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    deletedAt = null
                )
            )

            val files = listOf(
                File(
                    null,
                    measurement = measurements[0],
                    fileName = "measurement_1_data.json",
                    fileType = "application/json",
                    content = """{"example": "data"}""".toByteArray(),
                    metadata = """{"author": "admin", "tags": ["test", "measurement"]}"""
                ),
                File(
                    null,
                    measurement = measurements[1],
                    fileName = "measurement_2_image.png",
                    fileType = "image/png",
                    content = ByteArray(0),
                    metadata = """{"author": "editor", "tags": ["image", "analysis"]}"""
                )
            ).map { fileRepository.save(it) }

            logger.info("Mock data has been initialized")
        }
    }
}
