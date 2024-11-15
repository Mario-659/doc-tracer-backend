package dl.doctracer.dto.admin

data class UserUpdateRequest(
    val id: Int,
    val roles: List<String>,
    val isActive: Boolean
)
