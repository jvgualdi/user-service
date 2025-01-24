package tec.jvgualdi.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterEmployeeDTO (@NotBlank(message = "Name must not be empty") String name,
                                   @NotBlank(message = "email must no be empty") @Email String email,
                                   @NotBlank(message = "password must not be empty") String password) {
}
