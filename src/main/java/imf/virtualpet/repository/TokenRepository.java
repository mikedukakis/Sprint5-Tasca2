package imf.virtualpet.repository;

import imf.virtualpet.security.token.Token;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TokenRepository extends ReactiveMongoRepository<Token, String> {
    Flux<Token> findByUserIdAndExpiredFalseOrRevokedFalse(String userId);

    Mono<Token> findByToken(String token);
}
