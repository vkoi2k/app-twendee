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

    Page<Request> findByIsAcceptTrueAndDeletedFalse(Pageable pageable);

    List<Request> findByIsAcceptTrueAndDeletedFalse(Sort sort);

    Page<Request> findByIsAcceptFalseAndDeletedFalse(Pageable pageable);

    List<Request> findByIsAcceptFalseAndDeletedFalse(Sort sort);

    Page<Request> findByIsAcceptNullAndDeletedFalse(Pageable pageable);

    List<Request> findByIsAcceptNullAndDeletedFalse(Sort sort);


    List<Request> findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqualAndDeletedFalse(
            Date minDate, Date maxDate
    );

    Page<Request> findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqualAndDeletedFalse(
            Date minDate, Date maxDate, Pageable pageable
    );

    List<Request> findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqualAndDeletedFalseAndIsAcceptTrue(
            Date minDate, Date maxDate
    );

    Page<Request> findByTimeRequestGreaterThanEqualAndTimeRequestLessThanEqualAndDeletedFalseAndIsAcceptFalse(
            Date minDate, Date maxDate, Pageable pageable
    );




    Request findByRequestIdAndDeletedFalse(Integer requestId);

    Page<Request> findAllByDeletedFalse(Pageable pageable);

    List<Request> findAllByDeletedFalse(Sort sort);

    List<Request> findAllByTimeRequestBetween( Date minDate, Date maxDate);

    Page<Request> findAllByTimeRequestBetween( Date minDate, Date maxDate, Pageable pageable);



}
