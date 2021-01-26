package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendRequestRepository extends JpaRepository<Request, Integer> {
//    User findByEmail(String email);
}
