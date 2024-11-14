package udemy.springsecurityapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyLoans {

    @GetMapping("/myLoans")
    public String getLoanDetails() {
        return "Here are the Loans from the DB";
    }

}
