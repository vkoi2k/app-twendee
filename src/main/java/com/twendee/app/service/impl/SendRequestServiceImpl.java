package com.twendee.app.service.impl;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.SendRequestAbsenceOutsideDTO;
import com.twendee.app.model.dto.SendRequestCheckOutDTO;
import com.twendee.app.model.dto.SendRequestLateEarlyDTO;
import com.twendee.app.model.entity.*;
import com.twendee.app.reponsitory.SendRequestRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.SendRequestService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Message absenceOutside(SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(sendRequestAbsenceOutsideDTO.getEmail());
            Date date = new Date();
            ModelMapper modelMapper = new ModelMapper();
            Request request = modelMapper.map(sendRequestAbsenceOutsideDTO,Request.class);
            request.setTimeRequest(date);
            request.setUser(user);
            sendRequestRepository.save(request);
            return new Message("Send request successfully, requestId: "+request.getRequestId());
        }catch (Exception e){
            e.printStackTrace();
            return new Message("Send request failed");
        }
    }

    @Override
    public Message lateEarly(SendRequestLateEarlyDTO sendRequestLateEarlyDTO) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(sendRequestLateEarlyDTO.getEmail());
            Date date = new Date();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            LateEarly lateEarly = modelMapper.map(sendRequestLateEarlyDTO, LateEarly.class);
            Request request = modelMapper.map(sendRequestLateEarlyDTO, Request.class);
            request.setLateEarly(lateEarly);
            request.setTimeRequest(date);
            request.setUser(user);
            sendRequestRepository.save(request);
            return new Message("Send request successfully, requestId: "+request.getRequestId());
        }catch (Exception e){
            e.printStackTrace();
            return new Message("Send request failed");
        }
    }

    @Override
    public Message checkOutSupport(SendRequestCheckOutDTO sendRequestCheckOutDTO) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(sendRequestCheckOutDTO.getEmail());
            Date date = new Date();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            CheckoutSupport checkoutSupport = modelMapper.map(sendRequestCheckOutDTO, CheckoutSupport.class);
            Request request = modelMapper.map(sendRequestCheckOutDTO, Request.class);
            request.setCheckoutSupport(checkoutSupport);
            request.setTimeRequest(date);
            request.setUser(user);
            sendRequestRepository.save(request);
            return new Message("Send request successfully, requestId: "+request.getRequestId());
        }catch (Exception e){
            e.printStackTrace();
            return new Message("Send request failed");
        }
    }
}
