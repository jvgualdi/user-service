package tec.jvgualdi.user_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeeRequest(
        @Valid UserRequest user,
        @NotBlank @Size(min=3) String fullName,
        @NotBlank String phone
) {}