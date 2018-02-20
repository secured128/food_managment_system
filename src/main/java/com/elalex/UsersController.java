package com.elalex;

import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UsersController {

    private static final String template = "Hello, %s!";

    @RequestMapping(method = POST, path = "/login")
    public String Login(@RequestHeader(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }


    /*
    see : http://www.baeldung.com/spring-requestmapping
     */
    @RequestMapping(value = "/user/{id}", method = GET)
    @ResponseBody
    public String GetUser(@PathVariable String id) {
        User user = new User(id, "User N" + id);
        return user.toString();
    }

}