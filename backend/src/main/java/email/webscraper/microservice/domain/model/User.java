package email.webscraper.microservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private boolean admin;
}
