package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserIdNotIn(List<Integer> userIds);

    User getUserByEmail(String email);

}
