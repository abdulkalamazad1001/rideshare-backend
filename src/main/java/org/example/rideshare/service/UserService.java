package org.example.rideshare.service;

import org.example.rideshare.dto.LoginRequest;
import org.example.rideshare.dto.RegisterRequest;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER --> this is useful to register the candidate..
    public User register(RegisterRequest req) {

        // If username already exists.. if exists we will have to throw an error
        if (userRepository.findByUsername(req.getUsername()) != null) {
            throw new BadRequestException("Username already taken");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // encrypt password
        user.setRole(req.getRole());

        return userRepository.save(user);
    }

    // LOGIN / AUTHENTICATE --> for login authentication.. we can use this..
    public User authenticate(LoginRequest req) {

        User user = userRepository.findByUsername(req.getUsername());
        if (user == null) {
            throw new BadRequestException("Invalid credentials");
        }

        // Check password
        boolean matches = passwordEncoder.matches(req.getPassword(), user.getPassword());
        if (!matches) {
            throw new BadRequestException("Invalid credentials");
        }

        return user;
    }
}
