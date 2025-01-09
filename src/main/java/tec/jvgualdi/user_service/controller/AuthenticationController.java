package tec.jvgualdi.user_service.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tec.jvgualdi.user_service.dto.AuthRequestDTO;
import tec.jvgualdi.user_service.security.TokenServiceJWT;
import tec.jvgualdi.user_service.security.JWTTokenDTO;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenServiceJWT tokenService;

    @PostMapping
    public ResponseEntity<?> loginUser (@RequestBody @Valid AuthRequestDTO userAuthentication) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userAuthentication.login(), userAuthentication.password());
        Authentication authentication = manager.authenticate(authenticationToken);
        var jwtToken = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenDTO(jwtToken));
    }

}
