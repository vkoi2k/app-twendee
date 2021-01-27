package com.twendee.app.service;

import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StaffService {
    ResponseEntity<List<UserDTO>> findAllUser(Integer start, Integer limit);

    void delete(@PathVariable int id);

    void add(User user);

    User getDetail(@PathVariable int id);

    List<User> search(@PathVariable String KeyWord);

    void update(@PathVariable int id);

}
