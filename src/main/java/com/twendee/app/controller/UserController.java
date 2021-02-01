package com.twendee.app.controller;

import com.twendee.app.model.dto.*;
import com.twendee.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/checkin")
    public Message checkIn(@RequestBody EmailInput email){
        return userService.userCheckin(email.getEmail());
    }

    @PostMapping("/checkout")
    public Message checkOut(@RequestBody EmailInput email){
        return userService.userCheckout(email.getEmail());
    }


    //Lịch sử điểm danh theo tháng
    //start là số page (trang) bắt đầu từ 0
    @PostMapping("/history")
    public ResponseEntity<?> getHistory(@RequestBody HistoryInput historyInput,
                                                           @RequestParam(value = "page", required = false) Integer start,
                                                           @RequestParam(value = "limit", required = false) Integer limit){
        return userService.userHistory(historyInput, start, limit);
    }


    @PostMapping("/user/{email}")
    public ResponseEntity<?> updateProfile(@PathVariable String email,
                                         @RequestBody InputProfileDTO inputProfileDTO){
        return userService.updateProfile(inputProfileDTO, email);
    }



}
