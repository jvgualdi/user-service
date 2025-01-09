package tec.jvgualdi.user_service.dto;

import tec.jvgualdi.user_service.entity.User;

import java.time.LocalDateTime;

public record UserResponseDTO(Long id, String username, String email, LocalDateTime createAt, boolean active) {

    public UserResponseDTO(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.isActive());
    }
}
