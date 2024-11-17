package dl.doctracer.dto.sample

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class SpectralDataMapper {
    fun map(request: SpectralData): String {
        return ObjectMapper().writeValueAsString(request)
    }

    fun mapToResponse(data: String): SpectralData {
        return ObjectMapper().readValue(data, SpectralData::class.java)
    }
}
