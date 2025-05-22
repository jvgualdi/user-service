package tec.jvgualdi.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tec.jvgualdi.user_service.domain.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
