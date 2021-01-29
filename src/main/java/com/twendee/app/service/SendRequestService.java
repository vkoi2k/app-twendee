package com.twendee.app.service;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.SendRequestAbsenceOutsideDTO;
import com.twendee.app.model.dto.SendRequestCheckOutDTO;
import com.twendee.app.model.dto.SendRequestLateEarlyDTO;

public interface SendRequestService {

    Message absenceOutside(SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO);

    Message lateEarly(SendRequestLateEarlyDTO sendRequestLateEarlyDTO);

    Message checkOutSupport(SendRequestCheckOutDTO sendRequestCheckOutDTO);
//    User findByEmail(String email);
}
