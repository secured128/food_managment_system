package com.elalex;

import com.elalex.food.model.User;
import com.elalex.food.model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class UsersController {

    private static final String template = "Hello, %s!";

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(method = POST, path = "/login")
    public String Login(@RequestHeader(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    /*
    see : http://www.baeldung.com/spring-requestmapping

    1. insert user
    INSERT INTO users(	id, name)	VALUES (1,'ALEX');

    2. call API
    http://localhost:8090/user/1
     */
    @RequestMapping(value = "/user/{id}", method = GET)
    @ResponseBody
    public String GetUser(@PathVariable long id) {
        User user = usersRepository.findOne(id);
        return user.toString();
    }

    @RequestMapping(value = "/user/create/{name}", method = GET)
    @ResponseBody
    public String CreateUser(@PathVariable String name) {
        User user = new User(name,name);
        user =usersRepository.save(user);
        return user.toString();
    }


}