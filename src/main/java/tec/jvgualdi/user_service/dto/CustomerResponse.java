package tec.jvgualdi.user_service.dto;

import tec.jvgualdi.user_service.domain.entity.CustomerAddress;

public record CustomerResponse (Long id, String email, String fullName, CustomerAddress customerAddress, String phone) {
}
