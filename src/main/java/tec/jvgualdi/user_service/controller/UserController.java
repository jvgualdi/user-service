package tec.jvgualdi.user_service.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tec.jvgualdi.user_service.dto.UserListing;
import tec.jvgualdi.user_service.dto.UserRequest;
import tec.jvgualdi.user_service.entity.User;
import tec.jvgualdi.user_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<UserListing>> listUsers(){
        var usersList = userService.getAllUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

}
