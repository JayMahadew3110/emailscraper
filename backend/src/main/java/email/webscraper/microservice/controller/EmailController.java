package email.webscraper.microservice.controller;

import email.webscraper.microservice.business.EmailManager;
import email.webscraper.microservice.domain.responses.ScraperResponse;
import email.webscraper.microservice.persistence.entity.EmailEntity;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emails")
@AllArgsConstructor
public class EmailController {
    private EmailManager emailManager;

    @RateLimiter(name = "myRateLimiter", fallbackMethod = "fallbackMethod")
    @RolesAllowed({"DEFAULT_USER","ADMIN"})
    @GetMapping
    public List<EmailEntity> getEmails() {
        List<EmailEntity> emails = emailManager.getEmailsAsync().join();
        return emails;
    }

    @RolesAllowed({"DEFAULT_USER","ADMIN"})
    @GetMapping("scrape")
    public ScraperResponse scrapeEmails() {
        return emailManager.scrape();
    }
}
