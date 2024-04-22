package email.webscraper.microservice.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class GenericResponse {
    public boolean success;
    public String message;
    public Object result;
}
