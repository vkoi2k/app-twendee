package com.twendee.app.reponsitory;

import com.twendee.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //get All user chưa tạo instance timekeeping trong ngày hôm đó
    @Query(value = "SELECT * FROM user u WHERE u.user_id NOT IN (SELECT t.user_id FROM timekeeping t WHERE cast(t.date as date) =:date )", nativeQuery = true)
    public List<User> getUserNotIn(@Param("date") Date date);
}
