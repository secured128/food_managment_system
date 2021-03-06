package com.elalex.food.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Created by alexb on 2/20/2018.
 */
public interface UsersRepository extends CrudRepository<User, Long> {

    User findByNameAndPassword(String name, String password);

    List<User> findByNameStartsWithIgnoreCase(String name);


}
