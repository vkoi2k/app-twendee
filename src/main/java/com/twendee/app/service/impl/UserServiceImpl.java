package com.twendee.app.service.impl;

import com.twendee.app.model.dto.*;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.UserService;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TimeKeepingRepository timeKeepingRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.timeKeepingRepository = timeKeepingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Message userCheckin(String email) {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.getUserByEmailAndDeletedFalse(email));
            System.out.println("userID: " + user.get().getUserId());
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            TimeKeeping timeKeeping =
                    timeKeepingRepository.findByUser_UserIdAndDateGreaterThanEqualAndDateLessThanEqual(
                            user.get().getUserId(),
                            StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                            StringToDate.parse(DateToString.format(date) + " 23:59:59")
                    );
            System.out.println("tk: " + timeKeeping);
            if (timeKeeping != null) {
                if (timeKeeping.getCheckin() == null) {
                    timeKeeping.setCheckin(new Date());
                    timeKeepingRepository.save(timeKeeping);
                    return new Message("Checkin successful");
                }
                return new Message("You checked in at: " + timeKeeping.getCheckin().toString());
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return new Message("Checkin failed");
        }
        return new Message("Checkin failed");
    }

    @Override
    public Message userCheckout(String email) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(email);
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
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
                        timeKeeping.setCheckout(new Date());
                        timeKeepingRepository.save(timeKeeping);
                        return new Message("Checkout successful");
                    }
                    return new Message("You checked out at: " + timeKeeping.getCheckout().toString());
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return new Message("Checkout failed");
        }
        return new Message("Checkout failed");
    }

    @Override
    public ResponseEntity<?> userHistory(HistoryInput historyInput, Integer start, Integer limit) {
        try {
            User user = userRepository.getUserByEmailAndDeletedFalse(historyInput.getEmail());
            List<TimeKeeping> timeKeepingList;
            SimpleDateFormat DateToString = new SimpleDateFormat("01/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, historyInput.getMonth() - 1);
            cal.set(Calendar.YEAR, historyInput.getYear());
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
    public void forgotPassword(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {

        }
        String newPass = RandomStringUtils.randomAlphanumeric(6);
        user.get().setPass(passwordEncoder.encode(newPass));
        userRepository.save(user.get());
    }

    @Override
    public ResponseEntity<?> updateProfile(InputProfileDTO inputProfileDTO, String email) {

            try{
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.getConfiguration()
                        .setMatchingStrategy(MatchingStrategies.STRICT);
                User oldUser = userRepository.getUserByEmailAndDeletedFalse(email);
                User user = modelMapper.map(inputProfileDTO, User.class);
                user.setUserId(oldUser.getUserId());
                user.setRole(oldUser.isRole());
                user.setPass(oldUser.getPass());
                user.setEmail(oldUser.getEmail());
                user.setDob(sdf.parse(inputProfileDTO.getBirthday()));
                return ResponseEntity.ok(modelMapper.map(userRepository.save(user),UserDTO.class));

            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.ok(new Message("update failed"));
            }
        }



}
