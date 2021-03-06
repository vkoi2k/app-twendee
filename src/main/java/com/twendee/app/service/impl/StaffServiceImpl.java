package com.twendee.app.service.impl;

import com.twendee.app.model.dto.InputUserDTO;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
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

    final
    PasswordEncoder passwordEncoder;

    private final TimeKeepingRepository timeKeepingRepository;

    public StaffServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TimeKeepingRepository timeKeepingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.timeKeepingRepository = timeKeepingRepository;
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
            User newUser=userRepository.save(user);
            UserDTO newUserDTO = modelMapper.map(newUser, UserDTO.class);
            newUserDTO.setBirthday(user.getDob().getTime());

            //tạo timekeeping ngày hôm nay cho nhân viên mới tạo
            TimeKeeping timeKeeping=new TimeKeeping();
            timeKeeping.setUser(newUser);
            SimpleDateFormat removeTime=new SimpleDateFormat("dd/MM/yyyy");
            timeKeeping.setDate(
                    removeTime.parse(removeTime.format(new Date(new Date().getTime()+7*3600*1000)))
            );
            timeKeepingRepository.save(timeKeeping);

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
        List<User> users= userRepository.findByDeletedFalseAndNameLike(
                "%" + KeyWord + "%");
        List<User> usersEmail= userRepository.findByDeletedFalseAndEmailLike(
                "%" + KeyWord + "%");
        List<User> usersPhone= userRepository.findByDeletedFalseAndPhoneLike(
                "%" + KeyWord + "%");

        //kết hợp 3 list trên lại, loại bỏ phần tử trùng lặp
        for(User user: usersEmail){
            if(!users.contains(user)) users.add(user);
        }
        for(User user: usersPhone){
            if(!users.contains(user)) users.add(user);
        }

        //phân trang
        if(page!=null && limit !=null){
            users=pageable(users, page, limit);
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

    private List<User> pageable(List<User> users, Integer page, Integer limit){
        List<User> returnList=new ArrayList<>();
        if(page*limit>users.size()-1) return returnList;
        int endIndex= Math.min((page + 1) * limit, users.size());
        for(int i=page*limit;i<endIndex;i++){
            returnList.add(users.get(i));
        }
        return returnList;
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
            String newPass = user.getPass();
            if(newPass.equals(oldUser.getPass())){
                user.setPass(oldUser.getPass());
            }
            else {
                user.setPass(passwordEncoder.encode(newPass));
            }
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
