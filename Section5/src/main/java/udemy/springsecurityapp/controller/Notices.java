package udemy.springsecurityapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Notices {

    @GetMapping("/notices")
    public String getNoticeDetails() {
        return "Here are the notices from the DB";
    }

}
