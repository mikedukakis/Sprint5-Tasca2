package imf.virtualpet.controller;

import imf.virtualpet.entity.User;
import imf.virtualpet.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public Mono<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}
