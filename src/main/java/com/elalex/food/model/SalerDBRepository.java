package com.elalex.food.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by alexb on 2/20/2018.
 */
public interface SalerDBRepository extends CrudRepository<User, Long> {

    SalerDB findByNameAndPassword(String name, String password);

    List<SalerDB> findByNameStartsWithIgnoreCase(String name);


}
