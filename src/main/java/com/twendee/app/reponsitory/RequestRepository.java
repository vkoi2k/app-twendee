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

    Request findByRequestIdAndDeletedFalse(Integer requestId);

    Page<Request> findAllByDeletedFalse(Pageable pageable);

    List<Request> findAllByDeletedFalse(Sort sort);


}
