package com.twendee.app.service.impl;

import com.twendee.app.model.dto.*;
import com.twendee.app.model.entity.*;
import com.twendee.app.reponsitory.SendRequestRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.SendRequestService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SendRequestServiceImpl implements SendRequestService {

    private UserRepository userRepository;
    private SendRequestRepository sendRequestRepository;

    @Autowired
    public SendRequestServiceImpl(SendRequestRepository sendRequestRepository,UserRepository userRepository){
        this.sendRequestRepository = sendRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> absenceOutside(SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(sendRequestAbsenceOutsideDTO.getEmail());
            ModelMapper modelMapper = new ModelMapper();
            Request request = modelMapper.map(sendRequestAbsenceOutsideDTO,Request.class);
            request.setTimeRequest(new Date());
            request.setUser(user);
            sendRequestRepository.save(request);
            return ResponseEntity.ok(new RequestDTO(request));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new Message("Send request failed"));
        }
    }

    @Override
    public ResponseEntity<?> lateEarly(SendRequestLateEarlyDTO sendRequestLateEarlyDTO) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(sendRequestLateEarlyDTO.getEmail());
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            LateEarly lateEarly = modelMapper.map(sendRequestLateEarlyDTO, LateEarly.class);
            Request request = modelMapper.map(sendRequestLateEarlyDTO, Request.class);
            request.setLateEarly(lateEarly);
            request.setTimeRequest(new Date());
            request.setUser(user);
            sendRequestRepository.save(request);
            return ResponseEntity.ok(new RequestDTO(request));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new Message("Send request failed"));
        }
    }

    @Override
    public ResponseEntity<?> checkOutSupport(SendRequestCheckOutDTO sendRequestCheckOutDTO) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(sendRequestCheckOutDTO.getEmail());
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            CheckoutSupport checkoutSupport = modelMapper.map(sendRequestCheckOutDTO, CheckoutSupport.class);
            Request request = modelMapper.map(sendRequestCheckOutDTO, Request.class);
            request.setCheckoutSupport(checkoutSupport);
            request.setTimeRequest(new Date());
            request.setUser(user);
            sendRequestRepository.save(request);
            return ResponseEntity.ok(new RequestDTO(request));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new Message("Send request failed"));
        }
    }
}
