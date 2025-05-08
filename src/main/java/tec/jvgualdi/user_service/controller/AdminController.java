package tec.jvgualdi.user_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tec.jvgualdi.user_service.dto.EmployeeRequest;
import tec.jvgualdi.user_service.dto.UserResponse;
import tec.jvgualdi.user_service.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employees")
    public ResponseEntity<UserResponse> createEmployee(@RequestBody EmployeeRequest employeeDTO) {
        return ResponseEntity.ok(userService.createEmployee(employeeDTO));
    }
}
