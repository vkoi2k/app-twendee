package com.twendee.app.controller;

import com.twendee.app.model.dto.EmailInput;
import com.twendee.app.model.dto.HistoryInput;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@RequestBody EmailInput email){
        return userService.userCheckin(email.getEmail());
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkOut(@RequestBody EmailInput email){
        return userService.userCheckout(email.getEmail());
    }


    //Lịch sử điểm danh theo tháng
    //start bắt đầu từ 0
    @PostMapping("/history")
    public ResponseEntity<?> getHistory(@RequestBody HistoryInput historyInput,
                                                           @RequestParam(value = "start", required = false) Integer start,
                                                           @RequestParam(value = "limit", required = false) Integer limit){
        return userService.userHistory(historyInput, start, limit);
    }



}
