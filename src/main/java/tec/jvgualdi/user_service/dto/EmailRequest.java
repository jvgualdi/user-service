package tec.jvgualdi.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(@Email @NotBlank String to,
                           @NotBlank String subject,
                           @NotBlank String body) {
}
