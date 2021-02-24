package com.twendee.app.service;

import com.twendee.app.model.dto.InputForgotPassword;
import com.twendee.app.model.dto.InputProfileDTO;
import com.twendee.app.model.dto.InputToken;
import com.twendee.app.model.dto.Message;
import org.springframework.http.ResponseEntity;

public interface UserService {
    Message userCheckin(String email);

    Message userCheckout(String email);

    ResponseEntity<?> userHistory(String email, Integer month, Integer year, Integer start, Integer limit);

    ResponseEntity<?> forgotPassword(InputForgotPassword inputForgotPassword) ;

    ResponseEntity<?> updateProfile(InputProfileDTO inputProfileDTO, Integer id);

    ResponseEntity<?> profile(InputToken inputToken);




}
