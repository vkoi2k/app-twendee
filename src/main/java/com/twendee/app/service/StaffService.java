package com.twendee.app.service;

import com.twendee.app.model.dto.InputUserDTO;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface StaffService {
    List<UserDTO> findAllUser(Integer start, Integer limit);

    Message delete(Integer id);

    ResponseEntity<?> addStaff(InputUserDTO inputUserDTO);

    ResponseEntity<?> getDetail(Integer id);

    List<UserDTO> search(String KeyWord, Integer page, Integer limit);


    ResponseEntity<?> updateStaff(InputUserDTO inputUserDTO, Integer id);

}
