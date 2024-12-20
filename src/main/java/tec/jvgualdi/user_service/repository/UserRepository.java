package tec.jvgualdi.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tec.jvgualdi.user_service.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}