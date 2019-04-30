

package com.elalex.food.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alexb on 2/20/2018.
 */
@Repository
public interface StockDBRepository extends CrudRepository<StockDB, Long> {




    @Query(value=
            "select stock.*  from stock where product_id in (:ids)\n"+
                    "and quantity- coalesce(used_quantity,0)>0\n" +
                    " and date(expiration_date) >= CURRENT_DATE"
            , nativeQuery = true)

    List<StockDB> selectStockQuery(@Param("ids") List<Long> ids);



}


