package dl.doctracer.dto.admin

class UserResponse (
    val id: Int,
    val username: String,
    val email: String,
    val roles: List<String>,
    val isActive: Boolean,
    val lastLogin: String,
    val createdAt: String,
    val updatedAt: String,
)
