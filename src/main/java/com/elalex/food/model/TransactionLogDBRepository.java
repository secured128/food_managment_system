package com.elalex.food.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionLogDBRepository extends CrudRepository<TransactionLogDB, Long> {




    @Query(value=
            "select nextval('transaction_log_seq');"
            , nativeQuery = true)
    Integer getTransactionId();



}


