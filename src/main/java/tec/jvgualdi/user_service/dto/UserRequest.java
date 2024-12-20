package tec.jvgualdi.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotBlank(message = "username is required") @Size(max=50, min=3, message = "Username minimum size is 3 and maximum 50") String username,
                          @NotBlank(message = "email is required") @Size(max=50, min=5, message = "Email size must be between 5 and 50 characters")@Email String email,
                          @NotBlank(message = "password is required") @Size(min = 6, max = 20) String password) {
}
