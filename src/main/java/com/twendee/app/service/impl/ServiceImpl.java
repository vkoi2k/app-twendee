package com.twendee.app.service.impl;

import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.StaffRepository;
import com.twendee.app.service.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    @Autowired
    private StaffRepository staffRepository;

    public List<User> findAll(){
        return staffRepository.findAll();
    }

}
