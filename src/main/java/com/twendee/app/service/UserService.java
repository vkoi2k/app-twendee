package com.twendee.app.service;

import com.twendee.app.model.dto.HistoryInput;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    Message userCheckin(String email);
    Message userCheckout(String email);
    ResponseEntity<?> userHistory(HistoryInput historyInput, Integer start, Integer limit);

//    void forgotPassword(String userId) throws Exception;
//
    User updateUser(User user);


}
