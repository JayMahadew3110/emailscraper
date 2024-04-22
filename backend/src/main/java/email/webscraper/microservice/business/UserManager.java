package email.webscraper.microservice.business;

import email.webscraper.microservice.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserManager {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getUserById(Long id);
    UserEntity createUser (UserEntity user);
    void deleteUser(Long id);
    boolean CheckIfEmailTaken(String email);
    UserEntity promoteToAdmin(UserEntity user);
}
