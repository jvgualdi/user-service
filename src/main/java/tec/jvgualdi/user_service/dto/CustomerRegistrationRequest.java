package tec.jvgualdi.user_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record CustomerRegistrationRequest(
        //login data
        @NotBlank (message = "email is required") @Size(max=50, min=5, message = "Email size must be between 5 and 50 characters")@Email String email,
        @NotBlank(message = "password is required") @Size(min = 6, max = 20) String password,
        //profile data
        @NotBlank(message = "user name is required") @Size(max=50, min=3, message = "Customer name minimum size is 3 and maximum 50") String name,
        @NotBlank @JsonProperty("phone_number") @Pattern(regexp = "^\\+?[0-9]{8,15}$") String phoneNumber,
        @Valid CustomerAddressRequest address

) {
}
