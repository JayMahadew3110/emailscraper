package email.webscraper.microservice.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
