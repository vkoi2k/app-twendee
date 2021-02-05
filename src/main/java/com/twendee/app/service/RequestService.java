package com.twendee.app.service;

import com.twendee.app.model.dto.RequestDTO;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.Request;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<RequestDTO> findAll(Integer page, Integer limit);

    ResponseEntity<?> findById(Integer id);

    List<RequestDTO> findByIsAcceptTrue(Integer page, Integer limit);

    List<RequestDTO> findByIsAcceptFalse(Integer page, Integer limit);

    List<RequestDTO> findByType(String type, Integer page, Integer limit);

    List<RequestDTO> getListRequestByDate(Integer page, Integer limit, long dateInt);
}
