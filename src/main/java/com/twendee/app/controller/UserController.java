package com.twendee.app.controller;


import com.twendee.app.config.JwtTokenProvider;
import com.twendee.app.model.dto.*;
import com.twendee.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    final
    UserService userService;
    final
    JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {

        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/checkin")
    public Message checkIn(@RequestBody EmailInput email) {
        return userService.userCheckin(email.getEmail());
    }

    @PostMapping("/checkout")
    public Message checkOut(@RequestBody EmailInput email) {
        return userService.userCheckout(email.getEmail());
    }


    //Lịch sử điểm danh theo tháng
    //start là số page (trang) bắt đầu từ 0
    @PostMapping("/history")
    public ResponseEntity<?> getHistory(@RequestParam String email,
                                        @RequestParam(required = false) Integer month,
                                        @RequestParam(required = false) Integer year,
                                        @RequestParam(value = "page", required = false) Integer start,
                                        @RequestParam(value = "limit", required = false) Integer limit) {
        return userService.userHistory(email, month, year, start, limit);
    }


    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Integer id,
                                         @RequestBody InputProfileDTO inputProfileDTO){
        return userService.updateProfile(inputProfileDTO, id);
    }


    @PostMapping("/user")
    public ResponseEntity<?> profile(@RequestBody InputToken inputToken) {

        return userService.profile(inputToken);

    }


    @PostMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody InputForgotPassword inputForgotPassword) {

        return  userService.forgotPassword(inputForgotPassword);

    }


}
