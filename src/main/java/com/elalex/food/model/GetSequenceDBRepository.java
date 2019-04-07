

package com.elalex.food.model;

        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.stereotype.Repository;


@Repository
public interface GetSequenceDBRepository extends CrudRepository<GetSequenceDB, Long> {




    @Query(value=
            "select nextval('transaction_log_seq') sequence_id ;"
            , nativeQuery = true)
    GetSequenceDB getSequenceId();



}


