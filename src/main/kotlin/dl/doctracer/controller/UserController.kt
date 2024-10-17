package dl.doctracer.controller

import dl.doctracer.service.UserAuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userAuthService: UserAuthService) {

//    TODO admin-feature
//    @GetMapping
//    fun getAllUsers(): List<User> = userService.findAll()

//    TODO admin-feature
//    @GetMapping("/{id}")
//    fun getUserById(@PathVariable id: Int): ResponseEntity<User> {
//        val user = userService.findById(id)
//        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
//    }

//    TODO admin-feature
//    @PutMapping("/{id}")
//    fun updateUser(@PathVariable id: Int, @RequestBody updatedUser: User): ResponseEntity<User> {
//        val existingUser = userService.findById(id)
//        return if (existingUser != null) {
//            val userToUpdate = existingUser.copy(
//                username = updatedUser.username,
//                password = updatedUser.password,
//                email = updatedUser.email,
//                firstName = updatedUser.firstName,
//                lastName = updatedUser.lastName,
//                isSuperuser = updatedUser.isSuperuser,
//                lastLogin = updatedUser.lastLogin,
//                isActive = updatedUser.isActive,
//                deletedAt = updatedUser.deletedAt
//            )
//            ResponseEntity.ok(userService.save(userToUpdate))
//        } else {
//            ResponseEntity.notFound().build()
//        }
//    }

//    TODO admin-feature
//    @DeleteMapping("/{id}")
//    fun deleteUser(@PathVariable id: Int): ResponseEntity<Void> {
//    }
}
