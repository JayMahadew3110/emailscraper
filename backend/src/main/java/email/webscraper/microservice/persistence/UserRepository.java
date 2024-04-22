package email.webscraper.microservice.persistence;

import email.webscraper.microservice.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;;import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM users WHERE email=?1 LIMIT 1;", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);
}
