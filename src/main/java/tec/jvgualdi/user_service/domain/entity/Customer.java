package tec.jvgualdi.user_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "customer")
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Embedded
    private CustomerAddress address;

    @Column(nullable = false)
    private String phoneNumber;

}
