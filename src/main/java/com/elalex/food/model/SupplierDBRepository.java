package com.elalex.food.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by alexb on 2/20/2018.
 */
public interface SupplierDBRepository extends CrudRepository<SupplierDB, Long> {



    List<SupplierDB> findByNameStartsWithIgnoreCase(String name);


}
