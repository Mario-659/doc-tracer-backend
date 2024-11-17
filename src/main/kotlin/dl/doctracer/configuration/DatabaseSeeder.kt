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

@Configuration
@Profile("dev")
class DatabaseSeeder {
    private val logger: Logger = LoggerFactory.getLogger(DatabaseSeeder::class.java)

    @Bean
    fun seedDatabase(
        userAuthService: UserAuthService,
        userRepository: UserRepository,
        deviceService: DeviceService,
        spectraTypeService: SpectraTypeService,
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
                    }
                    else activeUser
                }
                .map { updatedUser -> userRepository.save(updatedUser) }

            var devices = listOf(
                Device(null, "VSC800-HS", "Designed to meet the requirements of immigration authorities, government agencies, and forensic science laboratories", "Foster + Freeman"),
                Device(null, "LCMS-2050", "High-Performance Liquid Chromatograph Mass Spectrometer", "Shimadzu"),
                Device(null, "NMR-500", "Nuclear Magnetic Resonance Spectrometer", "Bruker"),
                Device(null, "Cary 60 UV-Vis", "The Cary 60 UV-Vis spectrophotometer is a double-beam instrument with a powerful xenon lamp that flashes 80 times per second. The xenon lamp only illuminates the sample when data is acquired, protecting sensitive samples from photodegradation, and reducing power consumption. The highly focused beam is ideal for measuring small sample volumes accurately and reproducibly.", "Agilent"),
                Device(null, "inVia", "The ultimate research-grade confocal Raman microscope delivers outstanding performance and the best data in the shortest time", "RENISHAW")
            )
            devices = devices.map { deviceService.save(it) }

            var coveredMaterials = listOf(
                CoveredMaterial(null, "Ballpoint Ink", "Standard black ballpoint pen ink"),
                CoveredMaterial(null, "Gel Pen Ink", "Blue gel pen ink"),
                CoveredMaterial(null, "Laser Toner", "Black laser printer toner"),
                CoveredMaterial(null, "Fountain Pen Ink", "Green fountain pen ink"),
                CoveredMaterial(null, "Stamp Ink", "Red stamp ink")
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
                )
            ).map { measurementConditionsRepository.save(it) }

            val measurements = listOf(
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[0],
                    coveredMaterial = coveredMaterials[0],
                    user = users[0],
                    device = devices[0],
                    conditions = measurementConditions[0],
                    measurementDate = Instant.now(),
                    comments = "Test measurement"
                ),
                Measurement(
                    null,
                    coveringMaterial = coveringMaterials[1],
                    coveredMaterial = coveredMaterials[1],
                    user = users[1],
                    device = devices[1],
                    conditions = measurementConditions[0],
                    measurementDate = Instant.now(),
                    comments = "Another test measurement"
                )
            ).map { measurementRepository.save(it) }

            val samples = listOf(
                Sample(
                    null,
                    measurement = measurements[0],
                    name = "Sample A",
                    spectralData = """{"wavelengths": [400, 500, 600], "intensities": [0.1, 0.2, 0.3]}""",
                    type = SpectralType.REFLECTANCE
                ),
                Sample(
                    null,
                    measurement = measurements[1],
                    name = "Sample B",
                    spectralData = """{"wavelengths": [450, 550, 650], "intensities": [0.15, 0.25, 0.35]}""",
                    type = SpectralType.ABSORPTION
                )
            ).map { sampleRepository.save(it) }

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
                    content = ByteArray(0), // Replace with actual file content
                    metadata = """{"author": "editor", "tags": ["image", "analysis"]}"""
                )
            ).map { fileRepository.save(it) }

            logger.info("Mock data has been initialized")
        }
    }
}
