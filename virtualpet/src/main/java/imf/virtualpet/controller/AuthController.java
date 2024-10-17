package imf.virtualpet.controller;

import imf.virtualpet.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public Mono<String> token(Mono<Authentication> authentication) {
        return authentication.flatMap(auth -> {
            return tokenService.generateToken(auth);
        });
    }
}
