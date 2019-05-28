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
            "select distinct transaction_id, recipe_name , to_char(creation_date, 'dd-mm-yyyy HH:MI')creation_date " +
                    "from transaction_log " +
                    "order by transaction_id desc;"
                    , nativeQuery = true)
    List<TransactionLogDB> selectTransactionLog(String userEmail);

    @Query (value=
            "select *  " +
                    "from transaction_log " +
                    "where transaction_id = ?1 "+
                    "and COALESCE(cancel_ind,'N') = 'N'; "
            , nativeQuery = true)
    List<TransactionLogDB> selectTransactionLogByTransId(Long transaction_id);

    @Query (value=
            "update transaction_log " +
                    "set cancel_ind = 'Y' "+
                    "where transaction_id = ?1; "
            , nativeQuery = true)
    void upDateCancelIndToYes(Long transaction_id);
}



