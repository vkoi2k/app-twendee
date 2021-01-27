package com.twendee.app.service.impl;

import com.twendee.app.model.dto.InputUserDTO;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.StaffService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private final UserRepository userRepository;

    public StaffServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //get list off all staffs
    @Override
    public List<UserDTO> findAllUser(Integer page, Integer limit) {
        List<User> users = new ArrayList<>();
        if (page != null && limit != null) {
            Page<User> pages = userRepository.
                    findByDeletedFalse(PageRequest.of(page, limit, Sort.by("name")));
            users = pages.toList();
        } else {
            users = userRepository.findByDeletedFalse(Sort.by("name"));
        }
        ModelMapper modelMapper = new ModelMapper();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(modelMapper.map(user, UserDTO.class));
        }
        return userDTOS;
    }

    //delete 1 staff, input is an id
    @Override
    public Message delete(Integer id) {
        try {
            User optionalUser=userRepository.findByUserIdAndDeletedFalse(id);
            if(optionalUser !=null){
                optionalUser.setDeleted(true);
                userRepository.save(optionalUser);
                return new Message("Delete successfully, staffId: " + id);
            }else{
                return new Message("userId: " + id+" is not found.");
            }

        } catch (Exception e) {
            return new Message("Delete failed.");
        }
    }

    //add new user, input is user entity
    @Override
    public Message addStaff(InputUserDTO inputUserDTO) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            User user = modelMapper.map(inputUserDTO, User.class);
            user.setRole(false);
            User newUser = userRepository.save(user);
            return new Message("Add staff successfully, userId: " + newUser.getUserId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new Message("Add staff failed.");
        }
    }

    //get detail user, input is id
    @Override
    public ResponseEntity<?> getDetail(Integer id) {
        try {
            User user = userRepository.findByUserIdAndDeletedFalse(id);
            ModelMapper modelMapper = new ModelMapper();
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.ok(new Message("staff not found."));
        }
    }

    //search for staff by name, email or phone, input is string
    @Override
    public List<UserDTO> search(String KeyWord) {
        List<User> users = userRepository.findByNameLikeOrEmailLikeOrPhoneLikeAndDeletedFalse(
                "%" + KeyWord + "%", "%" + KeyWord + "%", "%" + KeyWord + "%");
        ModelMapper modelMapper = new ModelMapper();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(modelMapper.map(user, UserDTO.class));
        }
        return userDTOS;
    }

    @Override
    public ResponseEntity<?> updateStaff(InputUserDTO inputUserDTO, Integer id) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            User optinalUser = userRepository.findByUserIdAndDeletedFalse(id);
            User user = modelMapper.map(inputUserDTO, User.class);
            user.setUserId(optinalUser.getUserId());
            user.setRole(optinalUser.isRole());
            return ResponseEntity.ok(modelMapper.map(userRepository.save(user),UserDTO.class));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new Message("update failed"));
        }
    }


}
