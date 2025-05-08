package tec.jvgualdi.user_service.dto;

import tec.jvgualdi.user_service.domain.entity.User;

import java.time.LocalDateTime;

public record UserResponse(Long id, String username, String email, LocalDateTime createAt, boolean active) {

    public UserResponse(User user){
        this(user.getId(), user.getName(), user.getEmail(), user.getCreated(), user.isActive());
    }
}
