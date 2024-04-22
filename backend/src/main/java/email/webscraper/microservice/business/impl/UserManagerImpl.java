package email.webscraper.microservice.business.impl;

import email.webscraper.microservice.business.UserManager;
import email.webscraper.microservice.persistence.UserRepository;
import email.webscraper.microservice.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> getUserById(Long id)
    {
       return userRepository.findById(id);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        user.setAdmin(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }
    @Override
    public void deleteUser(Long id) {
        if (id == 1L)
            return;
        userRepository.deleteById(id);
    }

    @Override
    public boolean CheckIfEmailTaken(String email) {
        Optional<UserEntity> OptinalUserEntity = userRepository.findByEmail(email);

        if (OptinalUserEntity.isEmpty())
            return false;
        return true;
    }

    @Override
    public UserEntity promoteToAdmin(UserEntity user) {
        user.setAdmin(true);
            userRepository.save(user);
            return user;
    }
}
