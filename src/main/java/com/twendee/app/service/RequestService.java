package com.twendee.app.service;

import com.twendee.app.model.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    List<Request> findAll();
    Optional<Request> findById(Integer id);
}
