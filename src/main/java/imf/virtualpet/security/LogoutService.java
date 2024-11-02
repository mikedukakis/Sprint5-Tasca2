package imf.virtualpet.security;

import imf.virtualpet.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LogoutService implements ServerLogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        ServerHttpRequest request = exchange.getExchange().getRequest();
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.empty();
        }

        String jwt = authHeader.substring(7);

        return tokenRepository.findByToken(jwt)
                .flatMap(storedToken -> {
                    storedToken.setExpired(true);
                    storedToken.setRevoked(true);
                    return tokenRepository.save(storedToken).then();
                })
                .then()
                .contextWrite(ReactiveSecurityContextHolder.clearContext());
    }
}