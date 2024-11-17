package dl.doctracer.model

enum class SpectralType(val displayName: String) {
    REFLECTANCE("Reflectance"),
    ABSORPTION("Absorption"),
    FLUORESCENCE("Fluorescence"),
    TRANSMITTANCE("Transmittance"),
    NORMALISED("Normalised"),
    DIFFERENTIAL("Differential"),
    AVERAGE("Average");

    companion object {
        fun fromDisplayName(displayName: String): SpectralType? {
            return entries.find { it.displayName.equals(displayName, ignoreCase = true) }
        }
    }
}
