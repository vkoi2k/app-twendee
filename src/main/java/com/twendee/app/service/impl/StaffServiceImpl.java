package com.twendee.app.service.impl;

import com.twendee.app.model.dto.InputUserDTO;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.StaffService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public StaffServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffServiceImpl.class);
    //get list off all staffs
    @Override
    public List<UserDTO> findAllUser(Integer page, Integer limit) {
        List<User> users;
        if (page != null && limit != null) {
            Page<User> pages = userRepository.
                    findByDeletedFalse(PageRequest.of(page, limit, Sort.by("name")));
            users = pages.toList();
        } else {
            users = userRepository.findByDeletedFalse(Sort.by("name"));
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO=modelMapper.map(user, UserDTO.class);
            userDTO.setBirthday(user.getDob().getTime());
            userDTOS.add(userDTO);
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
    public ResponseEntity<?> addStaff(InputUserDTO inputUserDTO) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("");
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            User user = modelMapper.map(inputUserDTO, User.class);
            user.setRole(false);
            user.setPass(passwordEncoder.encode(inputUserDTO.getPass()));
            user.setDob(new Date(inputUserDTO.getBirthday()));
            UserDTO newUserDTO = modelMapper.map(userRepository.save(user), UserDTO.class);
            newUserDTO.setBirthday(user.getDob().getTime());
            return ResponseEntity.ok(newUserDTO);
        }catch (DataIntegrityViolationException ex){
            return ResponseEntity.ok(new Message("DUPLICATE_EMAIL"));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.toString());
            return ResponseEntity.ok(new Message("ADD_USER_FAILED"));
        }
    }

    //get detail user, input is id
    @Override
    public ResponseEntity<?> getDetail(Integer id) {
        try {
            User user = userRepository.findByUserIdAndDeletedFalse(id);
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setBirthday(user.getDob().getTime());
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.ok(new Message("staff not found."));
        }
    }

    //search for staff by name
    @Override
    public List<UserDTO> search(String KeyWord, Integer page, Integer limit) {
        List<User> users;
        if(page!=null && limit !=null){
            Page<User> userPage=userRepository.
                    findByDeletedFalseAndNameLike
                            ("%" + KeyWord + "%", PageRequest.of(page, limit));
            users= userPage.toList();
        }else
         users = userRepository.findByDeletedFalseAndNameLike(
                "%" + KeyWord + "%");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO=modelMapper.map(user, UserDTO.class);
            userDTO.setBirthday(user.getDob().getTime());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    public ResponseEntity<?> updateStaff(InputUserDTO inputUserDTO, Integer id) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            User oldUser = userRepository.findByUserIdAndDeletedFalse(id);
            User user = modelMapper.map(inputUserDTO, User.class);
            user.setUserId(oldUser.getUserId());
            user.setRole(oldUser.isRole());
            user.setPass(oldUser.getPass());
            user.setDob(new Date(inputUserDTO.getBirthday()));
            if(!inputUserDTO.getEmail().equals(oldUser.getEmail())){
                return ResponseEntity.ok(new Message("You may not change your email"));
            }
            user.setEmail(oldUser.getEmail());
            UserDTO updatedUserDTO=modelMapper.map(userRepository.save(user),UserDTO.class);
            updatedUserDTO.setBirthday(user.getDob().getTime());
            return ResponseEntity.ok(updatedUserDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Message("update failed"));
        }
    }
}
