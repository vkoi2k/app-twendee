package com.twendee.app.service;

import com.twendee.app.model.dto.*;
import com.twendee.app.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    Message userCheckin(String email);

    Message userCheckout(String email);

    ResponseEntity<?> userHistory(HistoryInput historyInput, Integer start, Integer limit);

    //    void forgotPassword(String userId) throws Exception;
//
    ResponseEntity<?> updateProfile(InputProfileDTO inputProfileDTO, String email);




}
