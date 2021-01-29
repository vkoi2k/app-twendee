package com.twendee.app.service.impl;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.RequestDTO;
import com.twendee.app.model.entity.Request;
import com.twendee.app.reponsitory.RequestRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private UserRepository userRepository;
    private RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository,UserRepository userRepository){
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<RequestDTO> findAll(Integer page, Integer limit) {
        List<Request> requests;
        if (page != null && limit != null) {
            Page<Request> pages = requestRepository.
                    findAll(PageRequest.of(page, limit, Sort.by("requestId")));
            requests = pages.toList();
        } else {
            requests = requestRepository.findAll(Sort.by("requestId"));
        }
        List<RequestDTO> requestDTOS=new ArrayList<>();
        for(Request request: requests){
            requestDTOS.add(new RequestDTO(request));
        }
        return requestDTOS;
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        try {
            Optional<Request> request = requestRepository.findById(id);
            RequestDTO requestDTO = new RequestDTO(request.get());
            return ResponseEntity.ok(requestDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new Message("Request not found."));
        }
    }
}
