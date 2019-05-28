package com.elalex.food.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionLogDBRepository extends CrudRepository<TransactionLogDB, Long> {




    @Query(value=
            "select nextval('transaction_log_seq');"
            , nativeQuery = true)
    Integer getTransactionId();



    @Query (value=
            "select *  " +
                    "from transaction_log " +
                    "where transaction_id = ?1 "+
                    " and COALESCE(cancel_ind,'N') = 'N' ; "
            , nativeQuery = true)
    List<TransactionLogDB> selectTransactionLogByTransId(Long transaction_id);


}



