package tec.jvgualdi.user_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tec.jvgualdi.user_service.domain.enums.UserRole;
import tec.jvgualdi.user_service.dto.RegisterEmployeeDTO;
import tec.jvgualdi.user_service.dto.UserResponseDTO;
import tec.jvgualdi.user_service.dto.UserRequestDTO;
import tec.jvgualdi.user_service.domain.entity.User;
import tec.jvgualdi.user_service.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createEmployee(RegisterEmployeeDTO employeeDTO) {
        if (userRepository.existsByEmail(employeeDTO.email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User employee = new User();
        employee.setEmail(employeeDTO.email());
        employee.setName(employeeDTO.name());
        employee.setPassword(passwordEncoder.encode(employeeDTO.password()));
        employee.setRole(UserRole.EMPLOYEE);
        employee.setVerified(true);

        return new UserResponseDTO(userRepository.save(employee));
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO user) {
        if (userRepository.existsByEmail(user.email())) {
            throw new IllegalArgumentException("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setName(user.name());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setRole(UserRole.CUSTOMER);
        return new UserResponseDTO(userRepository.save(newUser));
    }


    public Page<UserResponseDTO> getAllUsers(Pageable pageableUser) {
        return this.userRepository.findAllByActiveTrue(pageableUser).map(UserResponseDTO::new);
    }

    public UserResponseDTO getUserDetails(Long userId) {
        return new UserResponseDTO(this.userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    @Transactional
    public void deleteUser(Long userId){
        var user = this.userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setActive(false);
    }

}
