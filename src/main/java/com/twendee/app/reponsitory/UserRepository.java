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

    //get All user chưa tạo instance timekeeping trong ngày hôm đó
//    @Query(value = "SELECT * FROM user u WHERE u.user_id NOT IN (SELECT t.user_id FROM timekeeping t WHERE cast(t.date as date) = :date )", nativeQuery = true)
//    List<User> getUserNotIn(@Param("date") Date date);
    List<User> findByUserIdNotIn(List<Integer> userIds);

    User getUserByEmail(String email);

    Page<User> findAll(Pageable pageable);
    List<User> findAll(Sort sort);

    List<User> findByNameLikeOrEmailLikeOrPhoneLike(String name, String email, String phone);


}
