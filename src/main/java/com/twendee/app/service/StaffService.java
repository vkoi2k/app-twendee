package com.twendee.app.service;

import com.twendee.app.model.dto.InputUserDTO;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StaffService {
    ResponseEntity<List<UserDTO>> findAllUser(Integer start, Integer limit);

    Message delete(Integer id);

    Message addStaff(InputUserDTO inputUserDTO);

    ResponseEntity<?> getDetail(Integer id);

    ResponseEntity<List<UserDTO>> search(String KeyWord);

    ResponseEntity<?> updateStaff(InputUserDTO inputUserDTO, Integer id);

}
