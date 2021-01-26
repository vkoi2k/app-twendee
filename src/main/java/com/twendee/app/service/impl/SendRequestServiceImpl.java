package com.twendee.app.service.impl;

import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.SendRequestRepository;
import com.twendee.app.service.SendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SendRequestServiceImpl implements SendRequestService {

    private SendRequestRepository sendRequestRepository;

    @Autowired
    public SendRequestServiceImpl(SendRequestRepository sendRequestRepository){
        this.sendRequestRepository = sendRequestRepository;
    }

    @Override
    @Transactional
    public void create(Request request) {
        sendRequestRepository.save(request);
    }

//    @Override
//    public User findByEmail(String email) {
//        return sendRequestRepository.findByEmail(email);
//    }
}
