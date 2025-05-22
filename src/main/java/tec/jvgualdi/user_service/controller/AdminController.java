package tec.jvgualdi.user_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tec.jvgualdi.user_service.dto.EmployeeRequest;
import tec.jvgualdi.user_service.dto.EmployeeResponse;
import tec.jvgualdi.user_service.dto.UserResponse;
import tec.jvgualdi.user_service.service.EmployeeService;
import tec.jvgualdi.user_service.service.UserService;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "bearer-key")
public class AdminController {

    private final UserService userService;
    private final EmployeeService employeeService;

    public AdminController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> listUsers(@PageableDefault(size = 3) Pageable pageableUser) {
        var usersList = userService.getAllUsers(pageableUser);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> detailUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDetails(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/employees/create")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeDTO, UriComponentsBuilder uriBuilder) {
        var employeeDetail = employeeService.createEmployee(employeeDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(employeeDetail.id()).toUri();
        return ResponseEntity.created(uri).body(employeeDetail);
    }
}
