package imf.virtualpet.service;

import imf.virtualpet.entity.User;
import imf.virtualpet.repository.UserRepository;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

@Data
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<User> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Mono<User> loginUser(String userName, String password) {
        return userRepository.findByUserName(userName)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}
