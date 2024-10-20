package dl.doctracer.configuration

import dl.doctracer.dto.auth.RegisterRequest
import dl.doctracer.model.*
import dl.doctracer.repository.SampleRepository
import dl.doctracer.repository.SpectraRepository
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
        spectraRepository: SpectraRepository,
        deviceService: DeviceService,
        spectraTypeService: SpectraTypeService,
        sampleRepository: SampleRepository,
        coveredMaterialService: CoveredMaterialService,
        coveringMaterialService: CoveringMaterialService
    ): CommandLineRunner {
        return CommandLineRunner {
            logger.info("Initializing database with authentic mock data")

            val registerRequests = listOf(
                RegisterRequest("admin", "password1", "admin1@gmail.com", "Admin", "Administrator"),
                RegisterRequest("user1", "password1", "user1@gmail.com", "User", "One"),
                RegisterRequest("user2", "password2", "user2@gmail.com", "User", "Two")
            )
            val users = registerRequests.map { userAuthService.register(it) }

            var devices = listOf(
                Device(null, "VSC800", "Versatile spectral comparator", "Foster + Freeman"),
                Device(null, "LC-MS-200", "Liquid Chromatography Mass Spectrometry", "Shimadzu"),
                Device(null, "NMR-500", "Nuclear Magnetic Resonance Spectrometer", "Bruker"),
                Device(null, "UV-VIS-Pro", "UV-Visible Spectrophotometer", "Agilent"),
                Device(null, "RAMAN-Scope", "Raman Spectrometer", "Renishaw")
            )
            devices = devices.map { deviceService.save(it) }

            var spectraTypes = listOf(
                SpectraType(null, "IR", "Infrared Spectroscopy"),
                SpectraType(null, "GC-MS", "Gas Chromatography Mass Spectrometry"),
                SpectraType(null, "LC-MS", "Liquid Chromatography Mass Spectrometry"),
                SpectraType(null, "NMR", "Nuclear Magnetic Resonance"),
                SpectraType(null, "RAMAN", "Raman Spectroscopy"),
                SpectraType(null, "UV-VIS", "Ultraviolet-Visible Spectroscopy")
            )
            spectraTypes = spectraTypes.map { spectraTypeService.save(it) }

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

            var samples = listOf(
                Sample(null, "Sample A", Instant.now(), coveredMaterials[0], coveringMaterials[0], users[0]),
                Sample(null, "Sample B", Instant.now(), coveredMaterials[1], coveringMaterials[1], users[1]),
                Sample(null, "Sample C", Instant.now(), coveredMaterials[2], coveringMaterials[2], users[2]),
                Sample(null, "Sample D", Instant.now(), coveredMaterials[3], coveringMaterials[3], users[0]),
                Sample(null, "Sample E", Instant.now(), coveredMaterials[4], coveringMaterials[4], users[1])
            )
            samples = samples.map { sampleRepository.save(it) }

            val spectra = listOf(
                Spectra(null, "{ \"spectrumSample\": [100,200,300] }", Instant.now(), spectraTypes[0], devices[0], samples[0], users[0]),
                Spectra(null, "{ \"spectrumSample\": [150,250,350] }", Instant.now(), spectraTypes[1], devices[1], samples[1], users[1]),
                Spectra(null, "{ \"spectrumSample\": [200,300,400] }", Instant.now(), spectraTypes[2], devices[2], samples[2], users[2]),
                Spectra(null, "{ \"spectrumSample\": [120,220,320] }", Instant.now(), spectraTypes[3], devices[3], samples[3], users[0]),
                Spectra(null, "{ \"spectrumSample\": [130,230,330] }", Instant.now(), spectraTypes[4], devices[4], samples[4], users[1])
            )
            spectra.forEach { spectraRepository.save(it) }

            logger.info("Authentic mock data has been initialized")
        }
    }
}
