package dl.doctracer.service

import dl.doctracer.dto.admin.UserResponse
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.repository.RoleRepository
import dl.doctracer.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AdminUserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
) {
    fun getAllUsers(): List<UserResponse> =
        userRepository
            .findAll()
            .map { it ->
                UserResponse(
                    id = it.id ?: -1,
                    username = it.username,
                    email = it.email,
                    roles = it.roles.map { role -> role.roleName },
                    lastLogin = it.lastLogin.toString(),
                    createdAt = it.createdAt.toString(),
                    updatedAt = it.updatedAt.toString(),
                    isActive = it.isActive
                ) }

    @Transactional
    fun activateUser(userId: Int) {
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException() }
        userRepository.save(user.copy(isActive = true))
    }

    @Transactional
    fun deactivateUser(userId: Int) {
        val user = userRepository.findById(userId).orElseThrow { EntityNotFoundException() }
        userRepository.save(user.copy(isActive = false))
    }

    @Transactional
    fun assignRole(userId: Int, roleName: String) {
        val user = userRepository
            .findById(userId)
            .orElseThrow { EntityNotFoundException() }

        val role = roleRepository
            .findByRoleName(roleName)
            ?: throw IllegalArgumentException("Role not found")

        val updatedRoles = user.roles.toMutableSet().apply { add(role) }
        userRepository.save(user.copy(roles = updatedRoles))
    }

    @Transactional
    fun removeRole(userId: Int, roleName: String) {
        val user = userRepository
            .findById(userId)
            .orElseThrow { EntityNotFoundException() }

        val role = roleRepository
            .findByRoleName(roleName)
            ?: throw IllegalArgumentException("Role not found")

        if (!user.roles.contains(role)) {
            throw IllegalArgumentException("User does not have '$role' role")
        }

        val updatedRoles = user.roles - role
        userRepository.save(user.copy(roles = updatedRoles))
    }
}
