package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserIdNotIn(List<Integer> userIds);

    User getUserByEmail(String email);

    Page<User> findAll(Pageable pageable);
    List<User> findAll(Sort sort);

    List<User> findByNameLikeOrEmailLikeOrPhoneLike(String name, String email, String phone);


}
