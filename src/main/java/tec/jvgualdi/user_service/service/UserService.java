package tec.jvgualdi.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tec.jvgualdi.user_service.domain.entity.User;
import tec.jvgualdi.user_service.domain.enums.UserRole;
import tec.jvgualdi.user_service.domain.enums.UserStatus;
import tec.jvgualdi.user_service.dto.UserResponse;
import tec.jvgualdi.user_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(String email, String password, UserRole role) {

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        return userRepository.save(newUser);
    }


    public Page<UserResponse> getAllUsers(Pageable pageableUser) {
        var users = userRepository.findAllByVerifiedTrue(pageableUser);
        return users.map(user -> new UserResponse(user.getId(), user.getEmail(), user.getCreated(), user.isAccountNonExpired()));
    }

    public UserResponse getUserDetails(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new UserResponse(user.getId(), user.getEmail(), user.getCreated(), user.isAccountNonLocked());
    }

    @Transactional
    public void deleteUser(Long userId){
        var user = this.userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setStatus(UserStatus.DELETED);
    }

    @Transactional
    public void activateUser(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setStatus(UserStatus.ACTIVE);
        user.setVerified(true);
    }
}
