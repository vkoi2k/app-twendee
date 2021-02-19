package com.twendee.app.service.impl;


import com.twendee.app.component.MailSender;
import com.twendee.app.config.JwtTokenProvider;
import com.twendee.app.model.dto.*;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    final
    UserRepository userRepository;

    final
    TimeKeepingRepository timeKeepingRepository;

    final

    PasswordEncoder passwordEncoder;

    final
    MailSender mailSender;

    final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TimeKeepingRepository timeKeepingRepository, PasswordEncoder passwordEncoder, MailSender mailSender, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.timeKeepingRepository = timeKeepingRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.jwtTokenProvider = jwtTokenProvider;


    }

    @Override
    public Message userCheckin(String email) {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.getUserByEmailAndDeletedFalse(email));
            System.out.println("userID: " + user.get().getUserId());
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date(new Date().getTime()+7*3600*1000);
            TimeKeeping timeKeeping =
                    timeKeepingRepository.findByUser_UserIdAndDateGreaterThanEqualAndDateLessThanEqual(
                            user.get().getUserId(),
                            StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                            StringToDate.parse(DateToString.format(date) + " 23:59:59")
                    );
            System.out.println("tk: " + timeKeeping);
            if (timeKeeping != null) {
                if (timeKeeping.getCheckin() == null) {
                    timeKeeping.setCheckin(date);
                    timeKeepingRepository.save(timeKeeping);
                    return new Message("Checkin successful");
                }
                return new Message("You checked in at: " + timeKeeping.getCheckin().toString());
            }else{
                return new Message("Checkin failed");
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return new Message("Checkin failed");
        }
    }

    @Override
    public Message userCheckout(String email) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(email);
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date(new Date().getTime()+7*3600*1000);
            TimeKeeping timeKeeping =
                    timeKeepingRepository.findByUser_UserIdAndDateGreaterThanEqualAndDateLessThanEqual(
                            user.getUserId(),
                            StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                            StringToDate.parse(DateToString.format(date) + " 23:59:59")
                    );
            if (timeKeeping != null) {
                if (timeKeeping.getCheckin() == null) {
                    return new Message("You must checkin before checkout.");
                } else {
                    if (timeKeeping.getCheckout() == null) {
                        timeKeeping.setCheckout(date);
                        timeKeepingRepository.save(timeKeeping);
                        return new Message("Checkout successful");
                    }
                    return new Message("You checked out at: " + timeKeeping.getCheckout().toString());
                }
            }else return new Message("Checkout failed");
        } catch (Exception ex) {
            //ex.printStackTrace();
            return new Message("Checkout failed");
        }
    }

    @Override
    public ResponseEntity<?> userHistory(String email,Integer month, Integer year, Integer start, Integer limit) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(email);
            List<TimeKeeping> timeKeepingList;
            SimpleDateFormat DateToString = new SimpleDateFormat("01/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            if(month !=null && year !=null){
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,5);
                cal.set(Calendar.MONTH, month - 1);
                cal.set(Calendar.YEAR, year);
                System.out.println("cal chua cong: " + DateToString.format(cal.getTime()) + " 00:00:00");
                Date date = cal.getTime();
                cal.add(Calendar.MONTH, 1);
                System.out.println("cal: " + DateToString.format(cal.getTime()) + " 00:00:00");
                if (start != null && limit != null) {
                    Page<TimeKeeping> pages = timeKeepingRepository
                            .findByUserAndDateGreaterThanEqualAndDateLessThan(
                                    user,
                                    StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                                    StringToDate.parse(DateToString.format(cal.getTime()) + " 00:00:00"),
                                    PageRequest.of(start, limit, Sort.by("date").ascending())
                            );
                    timeKeepingList = pages.toList();
                } else {
                    timeKeepingList = timeKeepingRepository
                            .findByUserAndDateGreaterThanEqualAndDateLessThan(
                                    user,
                                    StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                                    StringToDate.parse(DateToString.format(cal.getTime()) + " 00:00:00"),
                                    Sort.by("date").ascending()
                            );
                }
            }else{
                if (start != null && limit != null) {
                    Page<TimeKeeping> pages = timeKeepingRepository
                            .findByUser(
                                    user,
                                    PageRequest.of(start, limit, Sort.by("date").ascending())
                            );
                    timeKeepingList = pages.toList();
                } else {
                    timeKeepingList = timeKeepingRepository
                            .findByUser(
                                    user,
                                    Sort.by("date").ascending()
                            );
                }
            }
            List<TimeKeepingDTO> timeKeepingDTOList = new ArrayList<>();
            for (TimeKeeping timeKeeping : timeKeepingList) {
                try {
                    timeKeepingDTOList.add(new TimeKeepingDTO(timeKeeping));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return ResponseEntity.ok(timeKeepingDTOList);
        } catch (Exception ex) {
            return ResponseEntity.ok(new Message("get history failed"));
        }
    }

    @Override
    public void forgotPassword(InputForgotPassword inputForgotPassword) {
        String email = inputForgotPassword.getEmail();
        User userCurrent = userRepository.getUserByEmail(email);
        Integer id = userCurrent.getUserId();
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {

        }
        String newPass = RandomStringUtils.randomAlphanumeric(6);
        user.get().setPass(passwordEncoder.encode(newPass));
        userRepository.save(user.get());
        mailSender.send(user.get().getEmail(),
                "Reset password ",
                "New password: " + newPass
        );

    }

    @Override
    public ResponseEntity<?> updateProfile(InputProfileDTO inputProfileDTO, Integer id) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            User oldUser = userRepository.findByUserIdAndDeletedFalse(id);
            User user = modelMapper.map(inputProfileDTO, User.class);
            user.setUserId(oldUser.getUserId());
            user.setRole(oldUser.isRole());
           // user.setPass(oldUser.getPass());
            String newPass = user.getPass();
            user.setPass(passwordEncoder.encode(newPass));

            user.setDob(new Date(inputProfileDTO.getBirthday()));
            user.setEmail(oldUser.getEmail());
            UserDTO updatedUserDTO = modelMapper.map(userRepository.save(user), UserDTO.class);
            updatedUserDTO.setBirthday(user.getDob().getTime());
            return ResponseEntity.ok(updatedUserDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new Message("update failed"));
        }
    }

    @Override
    public ResponseEntity<?> profile(InputToken inputToken) {
        String token = inputToken.getToken();
        try {
            User user = jwtTokenProvider.getUserFromToken(token);
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setBirthday(user.getDob().getTime());
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.ok(new Message("user not found."));
        }
    }


}
