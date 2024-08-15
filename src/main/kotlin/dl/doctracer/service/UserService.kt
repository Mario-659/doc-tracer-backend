package dl.doctracer.service

import dl.doctracer.model.User
import dl.doctracer.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Int): User? = userRepository.findById(id).orElse(null)

    fun findByUsername(username: String): User? = userRepository.findByUsername(username)

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)

    @Transactional
    fun save(user: User): User = userRepository.save(user)

    @Transactional
    fun deleteById(id: Int) = userRepository.deleteById(id)
}
