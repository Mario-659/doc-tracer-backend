package dl.doctracer.service

import dl.doctracer.dto.admin.UserResponse
import dl.doctracer.dto.admin.UserUpdateRequest
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.model.User
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
            .map { mapToResponse(it)}

    fun getUser(id: Int): UserResponse =
        userRepository
            .findById(id)
            .map { mapToResponse(it) }
            .orElseThrow { EntityNotFoundException() }

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
            .findByRoleName(roleName.uppercase())
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
            .findByRoleName(roleName.uppercase())
            ?: throw IllegalArgumentException("Role not found")

        if (!user.roles.contains(role)) {
            throw IllegalArgumentException("User does not have '$role' role")
        }

        val updatedRoles = user.roles - role
        userRepository.save(user.copy(roles = updatedRoles))
    }

    @Transactional
    fun updateUser(update: UserUpdateRequest) {
        val user = userRepository.findById(update.id).orElseThrow { EntityNotFoundException() }

        val rolesToAdd = update.rolesToAdd.map {
                roleRepository.findByRoleName(it.uppercase()) ?: throw IllegalArgumentException("Role $it not found")
            }.toSet()

        val rolesToRemove = update.rolesToRemove.map {
            roleRepository.findByRoleName(it.uppercase()) ?: throw IllegalArgumentException("Role $it not found")
        }.toSet()

        val updatedRoles = user.roles - rolesToRemove + rolesToAdd
        userRepository.save(user.copy(roles = updatedRoles, isActive = update.isActive))
    }

    private fun mapToResponse(user: User): UserResponse = UserResponse(
        id = user.id ?: -1,
        username = user.username,
        email = user.email,
        roles = user.roles.map { role -> role.roleName },
        lastLogin = user.lastLogin.toString(),
        createdAt = user.createdAt.toString(),
        updatedAt = user.updatedAt.toString(),
        isActive = user.isActive
    )
}
