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

            val spectralDataList = listOf(
                """
                [
                    { "wavelength": 400, "intensity": 26.06 },
                    { "wavelength": 401, "intensity": 26.42 },
                    { "wavelength": 402, "intensity": 26.82 },
                    { "wavelength": 403, "intensity": 27.27 },
                    { "wavelength": 404, "intensity": 27.71 },
                    { "wavelength": 405, "intensity": 28.16 },
                    { "wavelength": 406, "intensity": 28.65 },
                    { "wavelength": 407, "intensity": 29.17 },
                    { "wavelength": 408, "intensity": 29.72 },
                    { "wavelength": 409, "intensity": 30.28 },
                    { "wavelength": 410, "intensity": 30.85 }
                ]
                """.trimIndent(),
                """
                [
                    { "wavelength": 400, "intensity": 25.10 },
                    { "wavelength": 401, "intensity": 25.50 },
                    { "wavelength": 402, "intensity": 25.90 },
                    { "wavelength": 403, "intensity": 26.30 },
                    { "wavelength": 404, "intensity": 26.70 },
                    { "wavelength": 405, "intensity": 27.10 },
                    { "wavelength": 406, "intensity": 27.50 },
                    { "wavelength": 407, "intensity": 27.90 },
                    { "wavelength": 408, "intensity": 28.30 },
                    { "wavelength": 409, "intensity": 28.70 },
                    { "wavelength": 410, "intensity": 29.10 }
                ]
                """.trimIndent()
            )

            val samples = spectralDataList.mapIndexed { index, json ->
                Sample(
                    id = null,
                    measurement = measurements[index],
                    name = "Sample ${'A' + index}",
                    spectralData = json,
                    type = SpectralType.REFLECTANCE,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    deletedAt = null
                )
            }
            samples.forEach { sampleRepository.save(it) }


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
