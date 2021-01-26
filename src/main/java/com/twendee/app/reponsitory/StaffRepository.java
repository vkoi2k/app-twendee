package com.twendee.app.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import com.twendee.app.model.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StaffRepository extends JpaRepository<User, Integer> {
    public List <User> search(String KeyWord);
}
