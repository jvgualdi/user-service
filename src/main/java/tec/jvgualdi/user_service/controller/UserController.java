package tec.jvgualdi.user_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tec.jvgualdi.user_service.dto.UserResponseDTO;
import tec.jvgualdi.user_service.dto.UserRequestDTO;
import tec.jvgualdi.user_service.service.UserService;


@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    @Transactional
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO user, UriComponentsBuilder uriBuilder) {
        var userDetail = userService.createUser(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDetail.id()).toUri();

        return ResponseEntity.created(uri).body(userDetail);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> listUsers(@PageableDefault(size = 3) Pageable pageableUser) {
        var usersList = userService.getAllUsers(pageableUser);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> detailUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDetails(id));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
