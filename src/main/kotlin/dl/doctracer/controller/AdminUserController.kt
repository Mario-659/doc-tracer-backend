package dl.doctracer.controller

import dl.doctracer.dto.admin.UserResponse
import dl.doctracer.service.AdminUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
class AdminUserController(private val adminUserService: AdminUserService) {

    @GetMapping
    fun listUsers(): ResponseEntity<List<UserResponse>> {
        val users = adminUserService.getAllUsers()
        return ResponseEntity.ok(users)
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
}
