package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Page<Request> findByIsAcceptTrue(Pageable pageable);
    List<Request> findByIsAcceptTrue(Sort sort);

    Page<Request> findByIsAcceptFalse(Pageable pageable);
    List<Request> findByIsAcceptFalse(Sort sort);



}
