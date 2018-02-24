package com.elalex;

import com.elalex.food.model.User;
import com.elalex.food.model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * https://spring.io/guides/tutorials/bookmarks/
 */
@RestController
public class UsersController {

    private static final String template = "Hello, %s!";

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(method = POST, path = "/hellow")
    public String sayHellow(@RequestHeader(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    @RequestMapping(method = POST, path = "/login")
    public ResponseEntity<User> login(@RequestBody User userToFind) {
        try {
            User user = usersRepository.findByNameAndPassword(userToFind.getName(), userToFind.getPassword());
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = GET, path = "/find/{nameStart}")
    public ResponseEntity<List<User>> find(@PathVariable String nameStart) {
        try {
            List<User> users = usersRepository.findByNameStartsWithIgnoreCase(nameStart);
            if (users != null) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(method = GET, path = "/find")
    public ResponseEntity<List<User>> find() {
        try {
            List<User> usersList = new ArrayList<>();
            Iterable<User> users = usersRepository.findAll();
            if (users != null) {
                Iterator<User> userIterator = users.iterator();
                while (userIterator.hasNext()) {
                    usersList.add(userIterator.next());
                }
                return ResponseEntity.ok(usersList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
    public String getUser(@PathVariable long id) {
        User user = usersRepository.findOne(id);
        return user.toString();
    }

    @RequestMapping(value = "/user/create/{name}", method = GET)
    @ResponseBody
    public String CreateUser(@PathVariable String name) {
        User user = new User(name, name);
        user = usersRepository.save(user);
        return user.toString();
    }

    @RequestMapping(method = POST, path = "/user/create")
    public ResponseEntity<User> create(@RequestBody User userToCreate) {
        try {
            User user = new User(userToCreate.getName(), userToCreate.getPassword());
            user = usersRepository.save(user);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

//    @RequestMapping(value = "/user", method = RequestMethod.POST)
//    ResponseEntity<User> add(@RequestBody User userToFind) {
//        try {
//            User user = usersRepository.findByNameAndPassword(userToFind.getName(), userToFind.getPassword());
//            if (user != null) {
//                return ResponseEntity.ok(user);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }


}