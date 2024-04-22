package email.webscraper.microservice.business.impl;

import email.webscraper.microservice.business.AuthManager;
import email.webscraper.microservice.configuration.security.token.AccessTokenEncoder;
import email.webscraper.microservice.configuration.security.token.impl.AccessTokenImpl;
import email.webscraper.microservice.domain.responses.GenericResponse;
import email.webscraper.microservice.domain.responses.LoginResponse;
import email.webscraper.microservice.persistence.UserRepository;
import email.webscraper.microservice.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthManagerImpl implements AuthManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;


    @Override
    public GenericResponse login(String userName, String password) {
        Optional<UserEntity> user = userRepository.findByEmail(userName);
        if (user.isEmpty()) {
            return new GenericResponse(false, "User not found", new LoginResponse("", null));
        }
        if (!matchesPassword(password, user.get().getPassword())) {
            return new GenericResponse(false, "Wrong password", new LoginResponse("", null));
        }
        return new GenericResponse(true, "Success", new LoginResponse(generateAccessToken(user), user.get().getId()));
    }

    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateAccessToken(Optional<UserEntity> user) {
        if (user.isPresent()) {
            Long userId = user.get().getId();
            return accessTokenEncoder.encode(new AccessTokenImpl(user.get().getEmail(), userId, user.get().isAdmin()));
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

}