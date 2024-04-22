package email.webscraper.microservice.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ScraperResponse {
    private boolean success;
    private int emailsScraped;
    private int uniqueEmails;
}
