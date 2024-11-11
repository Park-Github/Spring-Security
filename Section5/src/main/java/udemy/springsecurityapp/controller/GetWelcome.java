package udemy.springsecurityapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetWelcome {

    @GetMapping("/welcome")
    public String sayWelcome() {
        return "Welcome to Spring Application with Security";
    }

}
