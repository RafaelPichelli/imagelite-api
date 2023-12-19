package io.github.RafaelPichelli.imageliteapi.domain.service;

import io.github.RafaelPichelli.imageliteapi.domain.AccessToken;
import io.github.RafaelPichelli.imageliteapi.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
    User save(User user);
    AccessToken authenticate(String email, String password);
}
