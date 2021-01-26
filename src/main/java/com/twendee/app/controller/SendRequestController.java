package com.twendee.app.controller;

import com.twendee.app.model.dto.SendRequestDTO;
import com.twendee.app.service.SendRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/send-request")
public class SendRequestController {

    private SendRequestService sendRequestService;

    public SendRequestController(SendRequestService sendRequestService){
        this.sendRequestService = sendRequestService;
    }

    @PostMapping("/dayoff")
    public void createRequestDayOff(SendRequestDTO sendRequestDTO){

    }
}
