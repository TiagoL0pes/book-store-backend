package com.system.store.dtos;

import com.system.store.models.User;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class UserDto {

    private String id;
    private String email;
    private String password;

    public UserDto(User user) {
        id = user.getId().toHexString();
        email = user.getEmail();
        password = user.getPassword();
    }

    public static Collection<UserDto> toList(Collection<User> collection) {
        return collection
                .stream()
                .map(user -> new UserDto(user))
                .collect(Collectors.toList());
    }
}
