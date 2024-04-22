package email.webscraper.microservice.controller;

import email.webscraper.microservice.business.AuthManager;
import email.webscraper.microservice.domain.responses.GenericResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthManager authManager;
    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestParam(value = "username", required = true)String userName, @RequestParam(value = "password", required = true)String password) {

        GenericResponse user = authManager.login(userName,password);
        return ResponseEntity.ok().body(user);
    }
}
