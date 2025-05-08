package tec.jvgualdi.user_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tec.jvgualdi.user_service.domain.enums.UserRole;
import tec.jvgualdi.user_service.domain.entity.User;
import tec.jvgualdi.user_service.dto.EmployeeRequest;
import tec.jvgualdi.user_service.dto.UserRequest;
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
    public UserResponse createEmployee(EmployeeRequest employeeDTO) {
        if (userRepository.existsByEmail(employeeDTO.user().email())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User employee = new User();
        employee.setEmail(employeeDTO.user().email());
        employee.setName(employeeDTO.user().name());
        employee.setPassword(passwordEncoder.encode(employeeDTO.user().password()));
        employee.setRole(UserRole.EMPLOYEE);
        employee.setVerified(true);

        return new UserResponse(userRepository.save(employee));
    }

    @Transactional
    public UserResponse createUser(UserRequest user) {
        if (userRepository.existsByEmail(user.email())) {
            throw new IllegalArgumentException("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(user.email());
        newUser.setName(user.name());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setRole(UserRole.CUSTOMER);
        return new UserResponse(userRepository.save(newUser));
    }


    public Page<UserResponse> getAllUsers(Pageable pageableUser) {
        return this.userRepository.findAllByActiveTrue(pageableUser).map(UserResponse::new);
    }

    public UserResponse getUserDetails(Long userId) {
        return new UserResponse(this.userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    @Transactional
    public void deleteUser(Long userId){
        var user = this.userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setActive(false);
    }

}
