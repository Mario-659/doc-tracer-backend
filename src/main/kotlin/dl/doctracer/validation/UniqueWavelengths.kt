package dl.doctracer.validation

import dl.doctracer.dto.sample.DataPoint
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueWavelengthValidator::class])
annotation class UniqueWavelengths(
    val message: String = "Wavelengths must be unique",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class UniqueWavelengthValidator : ConstraintValidator<UniqueWavelengths, List<DataPoint>> {
    override fun isValid(value: List<DataPoint>?, context: ConstraintValidatorContext): Boolean {
        value ?: return true
        val wavelengths = value.map { it.wavelength }
        return wavelengths.size == wavelengths.toSet().size
    }
}
