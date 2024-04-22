package email.webscraper.microservice.business;

import email.webscraper.microservice.domain.responses.GenericResponse;

public interface AuthManager {
    GenericResponse login (String userName, String password);
}
