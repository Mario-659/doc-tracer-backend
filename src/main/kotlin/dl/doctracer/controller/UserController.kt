package dl.doctracer.controller

import dl.doctracer.model.User
import dl.doctracer.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<User> = userService.findAll()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<User> {
        val user = userService.findById(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createUser(@RequestBody user: User): User = userService.save(user)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody updatedUser: User): ResponseEntity<User> {
        val existingUser = userService.findById(id)
        return if (existingUser != null) {
            val userToUpdate = existingUser.copy(
                username = updatedUser.username,
                password = updatedUser.password,
                email = updatedUser.email,
                firstName = updatedUser.firstName,
                lastName = updatedUser.lastName,
                isSuperuser = updatedUser.isSuperuser,
                lastLogin = updatedUser.lastLogin,
                isActive = updatedUser.isActive,
                deletedAt = updatedUser.deletedAt
            )
            ResponseEntity.ok(userService.save(userToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<Void> {
        return if (userService.findById(id) != null) {
            userService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
