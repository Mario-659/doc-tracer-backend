package dl.doctracer.configuration

import dl.doctracer.dto.auth.RegisterRequest
import dl.doctracer.model.*
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
class MockDataSeeder {
    private val logger: Logger = LoggerFactory.getLogger(MockDataSeeder::class.java)

    @Bean
    fun seedDatabase(
        userService: UserService,
        spectraService: SpectraService,
        deviceService: DeviceService,
        spectraTypeService: SpectraTypeService,
        sampleService: SampleService,
        coveredMaterialService: CoveredMaterialService,
        coveringMaterialService: CoveringMaterialService
    ): CommandLineRunner {
        return CommandLineRunner {
            logger.info("Initializing database with mock data")

            val registerRequests = listOf(
                RegisterRequest("admin", "password1", "admin1@gmail.com", "adminName", "adminSurname"), // TODO update when admin registration is implemented
                RegisterRequest("user1", "password1", "user1@gmail.com", "adminName", "adminSurname")
            )
            val users = registerRequests.map { userService.register(it) }

            var devices = listOf(
                Device(null, "Device A", "Description of Device A", "Manufacturer A"),
                Device(null, "Device B", "Description of Device B", "Manufacturer B")
            )
            devices = devices.map { deviceService.save(it) }

            var spectraTypes = listOf(
                SpectraType(null, "Type A", "Description of Type A"),
                SpectraType(null, "Type B", "Description of Type B")
            )
            spectraTypes = spectraTypes.map { spectraTypeService.save(it) }

            var coveredMaterials = listOf(
                CoveredMaterial(null, "Material A", "Description of Material A"),
                CoveredMaterial(null, "Material B", "Description of Material B")
            )
            coveredMaterials = coveredMaterials.map { coveredMaterialService.save(it) }

            var coveringMaterials = listOf(
                CoveringMaterial(null, "Covering A", "Description of Covering A", "Manufacturer A", "Red"),
                CoveringMaterial(null, "Covering B", "Description of Covering B", "Manufacturer B", "Blue")
            )
            coveringMaterials = coveringMaterials.map { coveringMaterialService.save(it) }

            var samples = listOf(
                Sample(null, "Sample A", Instant.now(), coveredMaterials[0], coveringMaterials[0], users[0]),
                Sample(null, "Sample B", Instant.now(), coveredMaterials[1], coveringMaterials[1], users[1]),
            )
            samples = samples.map { sampleService.save(it) }

            val spectra = listOf(
                Spectra(null, "{ \"spectrumSample\": [1,2,3] }", Instant.now(), spectraTypes[0], devices[0], samples[0], users[0]),
                Spectra(null, "{ \"spectrumSample\": [4,5,6] }", Instant.now(), spectraTypes[1], devices[1], samples[1], users[1])
            )
            spectra.forEach { spectraService.save(it) }

            logger.info("Mock data has been initialized")
        }
    }
}

