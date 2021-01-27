package com.twendee.app.service.impl;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.SendRequestAbsenceOutsideDTO;
import com.twendee.app.model.dto.SendRequestLateEarlyDTO;
import com.twendee.app.model.entity.AbsenceOutside;
import com.twendee.app.model.entity.LateEarly;
import com.twendee.app.model.entity.Request;
import com.twendee.app.reponsitory.SendRequestRepository;
import com.twendee.app.service.SendRequestService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SendRequestServiceImpl implements SendRequestService {

    private SendRequestRepository sendRequestRepository;

    @Autowired
    public SendRequestServiceImpl(SendRequestRepository sendRequestRepository){
        this.sendRequestRepository = sendRequestRepository;
    }

    @Override
    public Message AbsenceOutside(SendRequestAbsenceOutsideDTO sendRequestAbsenceOutsideDTO) {
        try {
            Date date = new Date();
            ModelMapper modelMapper = new ModelMapper();
            Request request = modelMapper.map(sendRequestAbsenceOutsideDTO,Request.class);
            request.setTimeRequest(date);
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
            Date date = new Date();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Request request = modelMapper.map(sendRequestLateEarlyDTO, Request.class);
            request.setTimeRequest(date);
            sendRequestRepository.save(request);
            return new Message("Send request successfully, requestId: "+request.getRequestId());
        }catch (Exception e){
            e.printStackTrace();
            return new Message("Send request failed");
        }
    }


//    @Override
//    public User findByEmail(String email) {
//        return sendRequestRepository.findByEmail(email);
//    }
}
