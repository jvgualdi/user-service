package tec.jvgualdi.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(@Email @NotBlank(message = "login is required") String login,
                          @NotBlank(message = "password is required") @Size(min=8,message="Senha min 8 chars")String password) {
}
