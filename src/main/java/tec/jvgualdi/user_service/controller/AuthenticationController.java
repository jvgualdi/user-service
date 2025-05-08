package tec.jvgualdi.user_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tec.jvgualdi.user_service.domain.entity.User;
import tec.jvgualdi.user_service.dto.AuthRequest;
import tec.jvgualdi.user_service.dto.UserResponse;
import tec.jvgualdi.user_service.security.TokenServiceJWT;
import tec.jvgualdi.user_service.security.JWTTokenDTO;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenService;

    public AuthenticationController(AuthenticationManager manager, TokenServiceJWT tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTTokenDTO> loginUser (@RequestBody @Valid AuthRequest userAuthentication) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userAuthentication.login(), userAuthentication.password());
        Authentication authentication = manager.authenticate(authenticationToken);
        var jwtToken = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenDTO(jwtToken));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponse(user));
    }

}
