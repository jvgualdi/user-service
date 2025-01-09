package tec.jvgualdi.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tec.jvgualdi.user_service.dto.UserResponseDTO;
import tec.jvgualdi.user_service.dto.UserRequestDTO;
import tec.jvgualdi.user_service.entity.User;
import tec.jvgualdi.user_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponseDTO createUser(UserRequestDTO user) {
        var userEntity = this.userRepository.findByEmail(user.email());
        if (userEntity.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setName(user.name());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        return new UserResponseDTO(this.userRepository.save(newUser));
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageableUser) {
        return this.userRepository.findAllByActiveTrue(pageableUser).map(UserResponseDTO::new);
    }

    public UserResponseDTO getUserDetails(Long userId) {
        return new UserResponseDTO(this.userRepository.getReferenceById(userId));
    }

    public void deleteUser(Long userId){
        var user = this.userRepository.getReferenceById(userId);
        user.setActive(false);
    }


}
