package com.twendee.app.controller;

import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.StaffRepository;
import com.twendee.app.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class StaffController {
    @Autowired
    private StaffService userService;

    //get list of all staffs
    @GetMapping("/staffs")
    public List <User> findAll() {
        return userService.findAll();
    }

    //delete 1 staff, input is id
    @DeleteMapping("/staffs/{id}")
    public ResponseEntity delete(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    //add staff, input is user entity
    @PostMapping("/staffs")
    public ResponseEntity add(User user){
        userService.add(user);
        //body(user) holds infor about the added entity
        return ResponseEntity.ok().body(user);
    }

    //get detail staff, input is id
    @GetMapping("staffs/{id}")
    public User getDetail(@PathVariable int id){
        return userService.getDetail(id);
    }

    //search by name, email or phone, input is string
    @GetMapping("staff?search={search}")
    public List<User> search(String KeyWord){
        return userService.search(KeyWord);
    }
}
