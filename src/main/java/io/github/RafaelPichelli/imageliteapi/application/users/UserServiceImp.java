package io.github.RafaelPichelli.imageliteapi.application.users;

import io.github.RafaelPichelli.imageliteapi.application.jwt.JwtService;
import io.github.RafaelPichelli.imageliteapi.domain.AccessToken;
import io.github.RafaelPichelli.imageliteapi.domain.entity.User;
import io.github.RafaelPichelli.imageliteapi.domain.exception.DuplicatedTupleException;
import io.github.RafaelPichelli.imageliteapi.domain.service.UserService;
import io.github.RafaelPichelli.imageliteapi.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        var possibleUser = getByEmail(user.getEmail());

        if (possibleUser != null){
            throw new DuplicatedTupleException("User already exists");
        }
        encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        var possibleUser = getByEmail(email);
        if (possibleUser == null){
            return null;
        }

        boolean matches = passwordEncoder.matches(password, possibleUser.getPassword());
        if (matches){
            return jwtService.generateToken(possibleUser);
        }

        return null;
    }

    private void encodePassword(User user){
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
    }
}
