package com.twendee.app.service;

import com.twendee.app.model.dto.*;
import freemarker.template.TemplateException;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {
    Message userCheckin(String email);

    Message userCheckout(String email);

    ResponseEntity<?> userHistory(HistoryInput historyInput, Integer start, Integer limit);

    void forgotPassword(Integer userId) throws TemplateException, IOException, MessagingException;

    ResponseEntity<?> updateProfile(InputProfileDTO inputProfileDTO, String email);




}
