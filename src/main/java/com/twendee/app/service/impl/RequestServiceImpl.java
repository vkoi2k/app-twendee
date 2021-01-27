package com.twendee.app.service.impl;

import com.twendee.app.model.entity.Request;
import com.twendee.app.reponsitory.RequestRepository;
import com.twendee.app.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository){
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Optional<Request> findById(Integer id) {
        return requestRepository.findById(id);
    }
}
