package email.webscraper.microservice.controller;

import email.webscraper.microservice.business.UserManager;
import email.webscraper.microservice.controller.converters.UserConvertor;
import email.webscraper.microservice.domain.model.User;
import email.webscraper.microservice.domain.responses.RegisterResponse;
import email.webscraper.microservice.persistence.entity.UserEntity;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:3001", "http://localhost:5173" })
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserManager userManager;

    @RolesAllowed({"ADMIN"})
    @GetMapping
    public List<User> getUsers() {
        List<UserEntity> users = userManager.getAllUsers();
        return users.stream().map(UserConvertor::convert).collect(Collectors.toList());
    }
    @RolesAllowed({"ADMIN"})
    @GetMapping("GetById")
    public ResponseEntity<User> getUserById(@RequestParam(value = "userId", required = true) Long userId) {
        Optional<UserEntity> user = userManager.getUserById(userId);
        return user.map(userEntity -> ResponseEntity.ok(UserConvertor.convert(userEntity)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable Long userId) {
        try {
            userManager.deleteUser(userId);
            UserEntity response = new UserEntity();
            response.setId(userId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserEntity());
        }
    }
    @RolesAllowed({"ADMIN"})
    @PostMapping("Create")
    public ResponseEntity<RegisterResponse> createUser(@RequestBody UserEntity request) {
        try {
            boolean emailTaken = userManager.CheckIfEmailTaken(request.getEmail());
            if (!emailTaken)
            {
                UserEntity user = userManager.createUser(request);
                boolean success = user.getId() > 0;
                return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(user.getId(), success, emailTaken));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(0L, false, emailTaken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @RolesAllowed({"ADMIN"})
    @PostMapping("promote")
    public Boolean promoteUser(@RequestParam(value = "userId", required = true) Long userId) {
        try {
            Optional<UserEntity> userEntity = userManager.getUserById(userId);
            if (!userEntity.isPresent())
                return false;
            userManager.promoteToAdmin(userEntity.get());
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
