package email.webscraper.microservice.business.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import email.webscraper.microservice.business.EmailManager;
import email.webscraper.microservice.domain.responses.ScraperResponse;
import email.webscraper.microservice.persistence.EmailRepository;
import email.webscraper.microservice.persistence.entity.EmailEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class EmailManagerImpl implements EmailManager {
    @Autowired
    EmailRepository emailRepository;

    //
    // THE API URL PROVIDED ONLY SHOWS THE COMMENTS OF ONE SPECIFIC POST. TO IMPROVE PERFORMANCE I USED A DIFFERENT URL THAT SHOWS ALL COMMENTS.
    // THE COMMENTED OUT CODE USES THE PROVIDED URL IN CASE THIS WAS REQUIRED
    //

    private final String URL = "https://jsonplaceholder.typicode.com/comments";

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public ScraperResponse scrape() {
        emailRepository.deleteAll();
        ScraperResponse response = new ScraperResponse(true, 0,0);
        saveEmails(requestApiData(response));
        return response;
    }
    public CompletableFuture<List<EmailEntity>> getEmailsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<EmailEntity> emails = new ArrayList<>();
            emails.addAll(emailRepository.findAll());
            return emails;
        });
    }
    @Cacheable("emailEntities")
    public List<EmailEntity> requestApiData(ScraperResponse response)
    {
        List<String> emails = new ArrayList<>();
        List<EmailEntity> emailEntities = new ArrayList<>();
        try
        {
            URL url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode postsJson = objectMapper.readTree(connection.getInputStream());

            for (JsonNode post : postsJson) {
                String email = post.get("email").asText();
                emails.add(email);
            }

            response.setEmailsScraped(emails.size());

            Set<String> set = new HashSet<>(emails);
            emails.clear();
            emails.addAll(set);

            response.setUniqueEmails(emails.size());
            for (String email : emails)
            {
                emailEntities.add(new EmailEntity(0L,email));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            response.setSuccess(false);
        }
        return emailEntities;
    }
    private boolean saveEmails(List <EmailEntity> emails)
    {
        try {
            emailRepository.saveAll(emails);
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /*
    @Override
    @EventListener(ApplicationReadyEvent.class)
    public ScraperResponse scrape() {
        List<String> emails = new ArrayList<>();
        ScraperResponse response = new ScraperResponse(true, 0,0,0);
        try
        {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode postsJson = objectMapper.readTree(connection.getInputStream());

            List<Integer> ids = new ArrayList<>();
            for (JsonNode post : postsJson) {
                int id = post.get("id").asInt();
                response.setPostsScraped(response.getPostsScraped() + 1);
                emails.addAll(getEmailsFromPost(id));
            }

            response.setEmailsScraped(emails.size());

            Set<String> set = new HashSet<>(emails);
            emails.clear();
            emails.addAll(set);

            response.setUniqueEmails(emails.size());

            System.out.println("reponse: " + response);

            for (String s:
                 emails) {
                System.out.println(s);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            response.setSuccess(false);
        }
        return response;
    }

    private List<String> getEmailsFromPost(int postId) {
        List<String> emails = new ArrayList<>();
        try
        {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts/" + postId + "/comments");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode postsJson = objectMapper.readTree(connection.getInputStream());

            for (JsonNode post : postsJson) {
                String email = post.get("email").asText();
                emails.add(email);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return emails;
    }
     */
}
