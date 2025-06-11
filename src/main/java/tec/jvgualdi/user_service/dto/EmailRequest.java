package tec.jvgualdi.user_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(@Email @NotBlank @JsonProperty("email_to") String emailTo,
                           @NotBlank String subject,
                           @NotBlank String body,
                            @NotBlank @JsonProperty("email_from") String emailFrom,
                           @NotBlank @JsonProperty("reply_to") String replyTo) {
}
