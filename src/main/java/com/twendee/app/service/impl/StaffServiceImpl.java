package com.twendee.app.service.impl;

import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.StaffRepository;
import com.twendee.app.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    //get list off all staffs
    @Override
    public List<User> findAll(){
        return staffRepository.findAll();
    }

    //delete 1 staff, input is an id
    @Override
    public void delete(@PathVariable int id){
        //get entity user with correct id then delete it
        User user = staffRepository.getOne(id);
        staffRepository.delete(user);
    }

    //add new user, input is user entity
    @Override
    public void add(User user){
        staffRepository.save(user);
    }

    //get detail user, input is id
    @Override
    public User getDetail(@PathVariable int id){
        return staffRepository.getOne(id);
    }

    //search for staff by name, email or phone, input is string
    @Override
    public List<User> search(String KeyWord){
        List<User> list = new ArrayList<User>();
        for(User obj: staffRepository.findAll()){
            if (obj.getName()==KeyWord) | (obj.getEmail()==KeyWord) | (obj.getPhone()==KeyWord){
                list.add(obj);
            }
        return list;
        }
    }
}
