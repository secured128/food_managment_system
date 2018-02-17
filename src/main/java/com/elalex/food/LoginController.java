package com.elalex.food;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class LoginController {

    private static final String template = "Hello, %s!";

    @RequestMapping(method = POST, path = "/login")
    public String Login(@RequestHeader(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }
}