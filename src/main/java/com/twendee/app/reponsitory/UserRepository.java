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

    List<User> findByUserIdNotInAndDeletedFalse(List<Integer> userIds);

    User getUserByEmailAndDeletedFalse(String email);

    Page<User> findByDeletedFalse(Pageable pageable);
    List<User> findByDeletedFalse(Sort sort);

    List<User> findByDeletedFalseAndNameLike(String name);
    Page<User> findByDeletedFalseAndNameLike(String name, Pageable pageable);
    List<User> findByDeletedFalseAndEmailLike(String email);
    List<User> findByDeletedFalseAndPhoneLike(String phone);


    User findByUserIdAndDeletedFalse(Integer userId);


    User getUserByEmail(String email);


}
