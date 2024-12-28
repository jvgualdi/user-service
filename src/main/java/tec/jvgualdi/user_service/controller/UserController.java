package tec.jvgualdi.user_service.controller;

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
import tec.jvgualdi.user_service.dto.UserDetails;
import tec.jvgualdi.user_service.dto.UserRequest;
import tec.jvgualdi.user_service.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetails> createUser(@RequestBody @Valid UserRequest user, UriComponentsBuilder uriBuilder) {
        var userDeail = userService.createUser(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDeail.id()).toUri();

        return ResponseEntity.created(uri).body(userDeail);
    }

    @GetMapping
    public ResponseEntity<Page<UserDetails>> listUsers(@PageableDefault(size = 3) Pageable pageableUser) {
        var usersList = userService.getAllUsers(pageableUser);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDetails> detailUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDetails(id));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
