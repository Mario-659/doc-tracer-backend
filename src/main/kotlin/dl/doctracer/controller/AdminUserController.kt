package dl.doctracer.controller

import dl.doctracer.dto.admin.UserResponse
import dl.doctracer.dto.admin.UserUpdateRequest
import dl.doctracer.service.AdminUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

// TODO block update requests for user requesting that so that someone would not block himself
@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
class AdminUserController(private val adminUserService: AdminUserService) {

    @GetMapping
    fun getUsers(): ResponseEntity<List<UserResponse>> {
        val users = adminUserService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): ResponseEntity<UserResponse> {
        val user = adminUserService.getUser(id)
        return ResponseEntity.ok(user)
    }

    @PutMapping("/{id}/activate")
    fun activateUser(@PathVariable id: Int): ResponseEntity<Void> {
        adminUserService.activateUser(id)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}/deactivate")
    fun deactivateUser(@PathVariable id: Int): ResponseEntity<Void> {
        adminUserService.deactivateUser(id)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{userId}/roles/{roleName}")
    fun assignRoleToUser(
        @PathVariable userId: Int,
        @PathVariable roleName: String
    ): ResponseEntity<Void> {
        adminUserService.assignRole(userId, roleName)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{userId}/roles/{roleName}")
    fun removeRoleFromUser(
        @PathVariable userId: Int,
        @PathVariable roleName: String
    ): ResponseEntity<Void> {
        adminUserService.removeRole(userId, roleName)
        return ResponseEntity.ok().build()
    }

//    TODO update that to be transactional
    @PutMapping("/bulk-update")
    fun updateUsers(@RequestBody updates: List<UserUpdateRequest>): ResponseEntity<Void> {
        updates.forEach { update ->
            adminUserService.updateUser(update)
        }
        return ResponseEntity.ok().build()
    }
}
