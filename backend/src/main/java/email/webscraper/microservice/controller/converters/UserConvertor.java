package email.webscraper.microservice.controller.converters;

import email.webscraper.microservice.domain.model.User;
import email.webscraper.microservice.persistence.entity.UserEntity;

public class UserConvertor {

    public static User convert(UserEntity user)
    {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .admin(user.isAdmin())
                .build();
    }

    public static UserEntity convert(User user)
    {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .admin(user.isAdmin())
                .build();
    }
}
