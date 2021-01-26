package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendRequestRepository extends JpaRepository<Request, Integer> {
}
