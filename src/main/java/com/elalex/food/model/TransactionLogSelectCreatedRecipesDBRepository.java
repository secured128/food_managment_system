
package com.elalex.food.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionLogSelectCreatedRecipesDBRepository extends CrudRepository<TransactionLogSelectCreatedRecipesDB, Long> {


    @Query (value=
            "select distinct transaction_id, recipe_name , to_char(creation_date, 'dd-mm-yyyy HH:MI')creation_date , user_email " +
                    "from transaction_log " +
                    "where user_email=?1 " +
                    "and creation_date >= to_timestamp(?2,'YYYY-MM-DD') "+
                    "and  creation_date < to_timestamp(?3,'YYYY-MM-DD')+ INTERVAL '1 day' " +
                    "order by transaction_id desc "
            , nativeQuery = true)
    List<TransactionLogSelectCreatedRecipesDB> selectTransactionLog(String userEmail, String fromDate, String toDate);
}


