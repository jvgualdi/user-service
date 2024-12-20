package tec.jvgualdi.user_service.entity;

import jakarta.persistence.*;
import lombok.*;
import tec.jvgualdi.user_service.dto.UserRequest;

import java.time.LocalDateTime;

@Entity(name = "User")
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


    public User(UserRequest user) {
        this.email = user.email();
        this.username = user.username();
        this.password = user.password();
    }
}
