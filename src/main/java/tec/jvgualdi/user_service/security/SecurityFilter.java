package tec.jvgualdi.user_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tec.jvgualdi.user_service.domain.entity.User;
import tec.jvgualdi.user_service.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenServiceJWT tokenServiceJWT;
    private final UserRepository userRepository;

    public SecurityFilter(TokenServiceJWT tokenServiceJWT, UserRepository userRepository) {
        this.tokenServiceJWT = tokenServiceJWT;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        if (token != null ) {
            var subject = tokenServiceJWT.getJWTSubject(token);
            var role = tokenServiceJWT.extractRole(token);

            User userAuth = userRepository.findByEmail(subject)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            var authentication = new UsernamePasswordAuthenticationToken(
                    userAuth,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authorizationHeader= request.getHeader("Authorization");

        return (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                ? authorizationHeader.replace("Bearer ", "")
                : null;
    }
}
