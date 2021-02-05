package com.twendee.app.controller;


import com.twendee.app.config.JwtTokenProvider;
import com.twendee.app.model.dto.*;

import com.twendee.app.model.dto.EmailInput;
import com.twendee.app.model.dto.HistoryInput;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.UserRepository;

import com.twendee.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestParam Integer id,
                                         @RequestBody InputProfileDTO inputProfileDTO){
        return userService.updateProfile(inputProfileDTO, id);
    }


    @PostMapping("/user")
    public ResponseEntity<?> profile(@RequestBody InputToken inputToken) {

        return userService.profile(inputToken);

    }



    @PostMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody InputForgotPassword inputForgotPassword){

        userService.forgotPassword(inputForgotPassword);
        return ResponseEntity.ok("Oke");
    }



}
