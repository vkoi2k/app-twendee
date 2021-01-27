package com.twendee.app.service.impl;

import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    private final UserRepository userRepository;

    public StaffServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //get list off all staffs
    @Override
    public ResponseEntity<List<UserDTO>> findAllUser(Integer page, Integer limit){
        List<User> users=new ArrayList<>();
        if(page !=null && limit != null){
            Page<User> pages= userRepository.
                    findAll(PageRequest.of(page, limit, Sort.by("name")));
            users= pages.toList();
        }else{
            users=userRepository.findAll(Sort.by("name"));
        }
        ModelMapper modelMapper=new ModelMapper();
        List<UserDTO> userDTOS=new ArrayList<>();
        for (User user:users){
            userDTOS.add(modelMapper.map(user,UserDTO.class));
        }
        return ResponseEntity.ok(userDTOS);
    }

    //delete 1 staff, input is an id
    @Override
    public void delete(@PathVariable int id){
        //get entity user with correct id then delete it
        User user = userRepository.getOne(id);
        userRepository.delete(user);
    }

    //add new user, input is user entity
    @Override
    public void add(User user){
        userRepository.save(user);
    }
    //get detail user, input is id
    @Override
    public User getDetail(@PathVariable int id){
        return userRepository.getOne(id);
    }

    //search for staff by name, email or phone, input is string
    @Override
    public List<User> search(@PathVariable String KeyWord){
        List<User> list = new ArrayList<User>();
        for(User obj: userRepository.findAll()){
            if ((obj.getName()==KeyWord) || (obj.getEmail()==KeyWord) || (obj.getPhone()==KeyWord)){
                list.add(obj);
            }
        }
        return list;
    }


    @Override
    public void update(@PathVariable int id){

    }
}
