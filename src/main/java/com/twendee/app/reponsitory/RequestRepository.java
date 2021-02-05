package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Page<Request> findByIsAcceptTrue(Pageable pageable);

    List<Request> findByIsAcceptTrue(Sort sort);

    Page<Request> findByIsAcceptFalse(Pageable pageable);

    List<Request> findByIsAcceptFalse(Sort sort);

    Page<Request> findByLateEarlyIsNotNull(Pageable pageable);

    List<Request> findByLateEarlyIsNotNull(Sort sort);

    Page<Request> findByCheckoutSupportIsNotNull(Pageable pageable);

    List<Request> findByCheckoutSupportIsNotNull(Sort sort);

    Page<Request> findByAbsenceOutsideIsNotNull(Pageable pageable);

    List<Request> findByAbsenceOutsideIsNotNull(Sort sort);

    List<Request> findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqual(
            Date minDate, Date maxDate
    );

    Page<Request> findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqual(
            Date minDate, Date maxDate, Pageable pageable
    );


}
