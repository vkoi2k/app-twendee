package com.twendee.app.controller;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.SendRequestAbsenceOutsideDTO;
import com.twendee.app.model.dto.SendRequestCheckOutDTO;
import com.twendee.app.model.dto.SendRequestLateEarlyDTO;
import com.twendee.app.model.entity.Request;
import com.twendee.app.service.SendRequestService;
import com.twendee.app.transform.SendRequestTransform;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send-request")
public class SendRequestController {

    private SendRequestService sendRequestService;

    public SendRequestController(SendRequestService sendRequestService){
        this.sendRequestService = sendRequestService;
    }

    @PostMapping("/day-off")
    public ResponseEntity<?> createRequestDayOff(@RequestBody SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO){
        sendRequestAbsenceOutsideDTO.setType(true);
        return sendRequestService.absenceOutside(sendRequestAbsenceOutsideDTO);
    }

    @PostMapping("/late-early")
    public ResponseEntity<?> createRequestLateEarly(@RequestBody SendRequestLateEarlyDTO sendRequestLateEarlyDTO){
        return sendRequestService.lateEarly(sendRequestLateEarlyDTO);
    }

    @PostMapping("/out-side")
    public ResponseEntity<?> createRequestOutSide(@RequestBody SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO){
        sendRequestAbsenceOutsideDTO.setType(false);
        return sendRequestService.absenceOutside(sendRequestAbsenceOutsideDTO);
    }

    @PostMapping("/check-out")
    public ResponseEntity<?> createRequestCheckOut(@RequestBody SendRequestCheckOutDTO sendRequestCheckOutDTO){
        return sendRequestService.checkOutSupport(sendRequestCheckOutDTO);
    }
}
