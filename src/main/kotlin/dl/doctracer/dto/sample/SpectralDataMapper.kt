package dl.doctracer.dto.sample

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class SpectralDataMapper {
    fun map(request: SpectralData): String {
        return ObjectMapper().writeValueAsString(request)
    }
}
