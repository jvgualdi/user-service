package tec.jvgualdi.user_service.dto;

import tec.jvgualdi.user_service.entity.User;

import java.time.LocalDateTime;

public record UserDetails(Long id, String username, String email, LocalDateTime createAt, boolean active) {

    public UserDetails (User user){
        this(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.isActive());
    }
}
