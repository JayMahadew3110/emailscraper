package email.webscraper.microservice.business;

import email.webscraper.microservice.domain.responses.ScraperResponse;
import email.webscraper.microservice.persistence.entity.EmailEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmailManager {
    ScraperResponse scrape();
    CompletableFuture<List<EmailEntity>> getEmailsAsync();
}
