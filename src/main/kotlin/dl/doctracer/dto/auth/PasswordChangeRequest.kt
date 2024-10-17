package dl.doctracer.dto.auth;

import jakarta.validation.constraints.NotBlank

data class PasswordChangeRequest(
    @field:NotBlank(message = "Old password is required")
    val oldPassword: String,

    @field:NotBlank(message = "New password is required")
    val newPassword: String
)
