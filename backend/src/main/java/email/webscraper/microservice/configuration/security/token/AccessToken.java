package email.webscraper.microservice.configuration.security.token;

public interface AccessToken {

    String getEmail();
    Long getUserId();
    boolean isAdmin();
}
