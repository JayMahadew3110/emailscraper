package email.webscraper.microservice.configuration.security;


import email.webscraper.microservice.configuration.exceptionhandler.RateLimitExceededException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class RateLimitConfig {

    private Map<String, Integer> requestCounts = new HashMap<>();

    @Before("execution(* email.webscraper.microservice.controller.*.*(..))")
    public void limitRequestRate(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        int count = requestCounts.getOrDefault(methodName, 0);
        if (count >= 100) { // Limiting to 10 requests per minute, adjust as needed
            throw new RateLimitExceededException("Rate limit exceeded");
        }
        requestCounts.put(methodName, count + 1);
    }

    @CacheEvict(allEntries = true, value = "requestCountsCache")
    public void clearCache() {}

    @Cacheable(value = "requestCountsCache", key = "#methodName")
    public int getRequestCount(String methodName) {
        return requestCounts.getOrDefault(methodName, 0);
    }
}
