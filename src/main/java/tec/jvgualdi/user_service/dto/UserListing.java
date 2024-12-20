package tec.jvgualdi.user_service.dto;

import tec.jvgualdi.user_service.entity.User;

public record UserListing(String username, String email) {

    public UserListing (User user){
        this(user.getUsername(), user.getEmail());
    }
}
