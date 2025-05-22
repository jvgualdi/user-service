package tec.jvgualdi.user_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerAddressRequest(@NotBlank @Size(min =3) String street,
                                     @NotBlank @Size(min = 3) String city,
                                     @NotBlank @Size(min = 2, max = 2) String state,
                                     @NotBlank @Size(min = 3) String country,
                                     @NotBlank @Size(min = 3) String neighborhood,
                                     @NotNull int number,
                                     @NotBlank @Size(min = 8, max = 8) @JsonProperty("zip_code") String zipCode) {
}
