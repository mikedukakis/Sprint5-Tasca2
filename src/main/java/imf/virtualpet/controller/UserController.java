package imf.virtualpet.controller;

import imf.virtualpet.dto.UserProfileResponseDTO;
import imf.virtualpet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/virtualpet/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public Mono<String> loginPage() {
        return Mono.just("login");
    }

    @GetMapping("/mypets")
    public Mono<String> myPetsPage() {
        return Mono.just("mypets");
    }

    @GetMapping("/profile")
    public Mono<UserProfileResponseDTO> getUserProfile(@RequestParam String username) {
        return userService.getUserByUsername(username)
                .map(user -> {
                    UserProfileResponseDTO response = new UserProfileResponseDTO();
                    response.setUsername(user.getUsername());
                    response.setRole(user.getRole());
                    return response;
                });
    }

}
