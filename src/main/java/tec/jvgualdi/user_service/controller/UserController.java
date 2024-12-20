package tec.jvgualdi.user_service.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tec.jvgualdi.user_service.dto.UserRequest;
import tec.jvgualdi.user_service.entity.User;
import tec.jvgualdi.user_service.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest user) {

        var userResponse = userRepository.save(new User(user));

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var usersList = userRepository.findAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

}
