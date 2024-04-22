package email.webscraper.microservice.configuration.security.token.impl;

import email.webscraper.microservice.configuration.security.token.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final Long userId;
    private final String email;
    private final boolean isAdmin;

    public AccessTokenImpl(String email, Long userId, boolean isAdmin) {
        this.email = email;
        this.userId = userId;
        this.isAdmin = isAdmin;
    }
}
