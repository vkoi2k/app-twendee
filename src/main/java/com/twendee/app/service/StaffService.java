package com.twendee.app.service;

import com.twendee.app.model.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StaffService {
    List<User> findAll();

    void delete(@PathVariable int id);

    void add(User user);

    User getDetail(@PathVariable int id);

    List<User> search(String KeyWord);

}
