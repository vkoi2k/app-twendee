package com.twendee.app.service.impl;

import com.twendee.app.model.dto.RequestDTO;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.RequestRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.RequestService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        ModelMapper modelMapper = new ModelMapper();
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
    public Optional<Request> findById(Integer id) {
        return requestRepository.findById(id);
    }
}
