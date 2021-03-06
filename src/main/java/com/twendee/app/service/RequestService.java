package com.twendee.app.service;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.RequestDTO;


import org.springframework.http.ResponseEntity;

import java.util.List;


public interface RequestService {
    List<RequestDTO> findAll(Integer page, Integer limit);

    ResponseEntity<?> findById(Integer id);

    List<RequestDTO> findByIsAcceptTrue(Integer page, Integer limit);

    List<RequestDTO> findByIsAcceptFalse(Integer page, Integer limit);

    List<RequestDTO> findByIsAcceptNull(Integer page, Integer limit);

    List<RequestDTO> findByType(String type, Integer page, Integer limit);

    List<RequestDTO> findByIsAcceptAndType(Integer isAccept , String type , Integer page , Integer limit);

    List<RequestDTO> findByIsAccpetAndDate(Integer isAccpet, long dateIntMin, long dateIntMax,Integer page, Integer limit);

    List<RequestDTO> findByTypeAndDate(String type, long dateIntMin, long dateIntMax, Integer page, Integer limit);

    List<RequestDTO> findByIsAccpetAndTypeAndDate(Integer isAccpet, String type, long dateIntMin, long dateIntMax, Integer page, Integer limit);


    List<RequestDTO> getListRequestByDate(Integer page, Integer limit, long dateIntMin, long dateIntMax);

    Message delete(Integer id);

    List<RequestDTO> searchRequest(String search, Integer page, Integer limit);
}
