package com.twendee.app.service;

import com.twendee.app.model.dto.*;
import org.springframework.http.ResponseEntity;

public interface SendRequestService {

    ResponseEntity<?> absenceOutside(SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO);

    ResponseEntity<?> lateEarly(SendRequestLateEarlyDTO sendRequestLateEarlyDTO);

    ResponseEntity<?> checkOutSupport(SendRequestCheckOutDTO sendRequestCheckOutDTO);
//    User findByEmail(String email);
}
