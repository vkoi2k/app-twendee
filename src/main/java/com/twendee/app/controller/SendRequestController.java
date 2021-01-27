package com.twendee.app.controller;

import com.twendee.app.model.dto.SendRequestAbsenceOutsideDTO;
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
    public ResponseEntity<HttpStatus> createRequestDayOff(@RequestBody SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO){
//        User user = sendRequestService.findByEmail(email);
        SendRequestTransform transform = new SendRequestTransform();
//        Request r = transform.dayOff(user,sendRequestDayOffDTO);
        Request r = transform.dayOff(sendRequestAbsenceOutsideDTO);
        sendRequestService.create(r);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }

    @PostMapping("/late-early")
    public ResponseEntity<HttpStatus> createRequestLateEarly(@RequestBody SendRequestLateEarlyDTO sendRequestLateEarlyDTO){
//        User user = sendRequestService.findByEmail(email);
        SendRequestTransform transform = new SendRequestTransform();
//        Request r = transform.dayOff(user,sendRequestDayOffDTO);
        Request r = transform.lateEarly(sendRequestLateEarlyDTO);
        sendRequestService.create(r);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }

    @PostMapping("/out-side")
    public ResponseEntity<HttpStatus> createRequestOutSide(@RequestBody SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO){
//        User user = sendRequestService.findByEmail(email);
        SendRequestTransform transform = new SendRequestTransform();
//        Request r = transform.dayOff(user,sendRequestDayOffDTO);
        Request r = transform.outSide(sendRequestAbsenceOutsideDTO);
        sendRequestService.create(r);
        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
    }
}
