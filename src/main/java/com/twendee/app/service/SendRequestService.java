package com.twendee.app.service;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.SendRequestAbsenceOutsideDTO;
import com.twendee.app.model.dto.SendRequestLateEarlyDTO;

public interface SendRequestService {

    Message AbsenceOutside(SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO);

    Message lateEarly(SendRequestLateEarlyDTO sendRequestLateEarlyDTO);

//    User findByEmail(String email);
}
