package imf.virtualpet.service;

import imf.virtualpet.domain.user.User;
import imf.virtualpet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Mono<User> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }
}
