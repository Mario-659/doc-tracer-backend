package dl.doctracer.configuration

import dl.doctracer.model.Role
import dl.doctracer.repository.RoleRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RoleInitializer(
    private val roleRepository: RoleRepository
) {
    @EventListener(ApplicationReadyEvent::class)
    fun initRoles() {
        val roles = listOf("ADMIN", "EDITOR", "VIEWER")
        roles.forEach { roleName ->
            roleRepository.findByRoleName(roleName) ?: roleRepository.save(Role(roleName = roleName))
        }
    }
}
