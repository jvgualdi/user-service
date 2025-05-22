package tec.jvgualdi.user_service.dto;

import jakarta.validation.constraints.*;

public record EmployeeRequest(
        @NotBlank (message = "email is required") @Size(max=50, min=5, message = "Email size must be between 5 and 50 characters")@Email String email,
        @NotBlank(message = "password is required") @Size(min = 6, max = 20) String password,
        @NotBlank @Size(min=3) String fullName) {}