package email.webscraper.microservice.configuration.exceptionhandler;
public class RateLimitExceededException extends RuntimeException {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
