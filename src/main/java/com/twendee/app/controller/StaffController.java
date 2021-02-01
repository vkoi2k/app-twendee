package com.twendee.app.controller;

import com.twendee.app.model.dto.InputUserDTO;
import com.twendee.app.model.dto.Message;
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
    public List<UserDTO> getAllUsers(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit
            ) {
        return userService.findAllUser(page, limit);
    }

    //delete 1 staff, input is id
    @DeleteMapping("/staffs/{id}")
    public Message delete(@PathVariable Integer id){
        return userService.delete(id);
    }

    @PostMapping("/staffs")
    public ResponseEntity<?> addStaff(@RequestBody InputUserDTO inputUserDTO){
        return userService.addStaff(inputUserDTO);
    }

    //get detail staff, input is id
    @GetMapping("/staffs/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id){
        return userService.getDetail(id);
    }

    //search by name, email or phone, input is string
    @GetMapping(value = "/staffs", params = "search")

    public List<UserDTO> search(@RequestParam String search,
                                @RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "limit", required = false) Integer limit){
        return userService.search(search, page, limit);
    }

    @PostMapping("/staffs/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Integer id,
                               @RequestBody InputUserDTO inputUserDTO){
        return userService.updateStaff(inputUserDTO, id);
    }
}
