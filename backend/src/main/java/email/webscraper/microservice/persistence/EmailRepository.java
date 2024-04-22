package email.webscraper.microservice.persistence;

import email.webscraper.microservice.persistence.entity.EmailEntity;
import email.webscraper.microservice.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
}
