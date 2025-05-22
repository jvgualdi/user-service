package tec.jvgualdi.user_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tec.jvgualdi.user_service.domain.entity.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenServiceJWT {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    public String generateToken(Authentication authentication) {
        var user = (User) authentication.getPrincipal();

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer("e-commerce")
                    .withSubject(user.getEmail())
                    .withExpiresAt(authExpiration())
                    .withClaim("role", user.getRole().name())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
    }

    private Instant authExpiration() {
        return Instant.now().plus(2, ChronoUnit.HOURS);
    }

    public String generateEmailVerificationToken(User user) {
        Algorithm alg = Algorithm.HMAC256(jwtSecret);
        return JWT.create()
                .withIssuer("e-commerce-verification")
                .withSubject(String.valueOf(user.getId()))
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .sign(alg);
    }

    public Long validateEmailVerificationToken(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(jwtSecret);
            DecodedJWT jwt = JWT.require(alg)
                    .withIssuer("e-commerce-verification")
                    .build()
                    .verify(token);
            return Long.parseLong(jwt.getSubject());
        } catch (JWTVerificationException ex) {
            throw new IllegalArgumentException("Invalid or expired verification token");
        }
    }

    public String getJWTSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer("e-commerce")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid or expired JWT token");
        }
    }


}
