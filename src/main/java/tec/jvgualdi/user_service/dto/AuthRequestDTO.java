package tec.jvgualdi.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(@Email @NotBlank(message = "login is required") String login,
                             @NotBlank(message = "password is required")String password) {
}
