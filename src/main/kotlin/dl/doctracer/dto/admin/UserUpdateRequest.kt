package dl.doctracer.dto.admin

data class UserUpdateRequest(
    val id: Int,
    val rolesToAdd: List<String>,
    val rolesToRemove: List<String>,
    val isActive: Boolean
)
