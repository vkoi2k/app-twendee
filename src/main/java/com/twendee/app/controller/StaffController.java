package com.twendee.app.controller;

import com.twendee.app.model.dto.InputUserDTO;
import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.User;
import com.twendee.app.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class StaffController {
    private final StaffService userService;

    public StaffController(StaffService userService) {
        this.userService = userService;
    }

    //get list of all staffs
    //page là số trang bắt đầu từ 0
    //limit là số bản ghi trong 1 trang
    @GetMapping("/staffs")
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
            ) {
        return userService.findAllUser(page, limit);
    }

    //delete 1 staff, input is id
    @DeleteMapping("/staffs/{id}")
    public ResponseEntity delete(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    //add staff, input is user entity
//    @PostMapping("/staffs")
//    public  add(@ResponseBody InputUserDTO inputUserDTO){
//
//    }

    //get detail staff, input is id
    @GetMapping("staffs/{id}")
    public User getDetail(@PathVariable int id){
        return userService.getDetail(id);
    }

    //search by name, email or phone, input is string
    @GetMapping("staffs?search={search}")
    public List<User> search(@PathVariable String search){
        return userService.search(search);
    }
}
