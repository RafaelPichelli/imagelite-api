package io.github.RafaelPichelli.imageliteapi.application.users;

import io.github.RafaelPichelli.imageliteapi.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto dto){
        return new User().builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }
}
