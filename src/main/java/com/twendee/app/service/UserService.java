package com.twendee.app.service;

import com.twendee.app.model.dto.*;
import com.twendee.app.model.entity.User;
import io.swagger.models.auth.In;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    Message userCheckin(String email);

    Message userCheckout(String email);

    ResponseEntity<?> userHistory(String email, Integer month, Integer year, Integer start, Integer limit);

    void forgotPassword(InputForgotPassword inputForgotPassword) ;

    ResponseEntity<?> updateProfile(InputProfileDTO inputProfileDTO, Integer id);

    ResponseEntity<?> profile(InputToken inputToken);




}
