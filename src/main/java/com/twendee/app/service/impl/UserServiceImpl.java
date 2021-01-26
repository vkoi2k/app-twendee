package com.twendee.app.service.impl;

import com.twendee.app.model.dto.HistoryInput;
import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TimeKeepingRepository timeKeepingRepository) {
        this.userRepository = userRepository;
        this.timeKeepingRepository = timeKeepingRepository;
    }

    @Override
    public ResponseEntity<?> userCheckin(String email) {
        try {
            Optional<User> user = Optional.ofNullable(userRepository.getUserByEmail(email));
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
                    return ResponseEntity.ok(new Message("Checkin successful"));
                }
                return ResponseEntity.ok(new Message("You checked in at: " + timeKeeping.getCheckin().toString()));
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return ResponseEntity.ok(new Message("Checkin failed"));
        }
        return ResponseEntity.ok(new Message("Checkin failed"));
    }

    @Override
    public ResponseEntity<?> userCheckout(String email) {
        try {
            User user = userRepository.getUserByEmail(email);
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
                    return ResponseEntity.ok(new Message("You must checkin before checkout."));
                } else {
                    if (timeKeeping.getCheckout() == null) {
                        timeKeeping.setCheckout(new Date());
                        timeKeepingRepository.save(timeKeeping);
                        return ResponseEntity.ok(new Message("Checkout successful"));
                    }
                    return ResponseEntity.ok(new Message("You checked out at: " + timeKeeping.getCheckout().toString()));
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            return ResponseEntity.ok(new Message("Checkout failed"));
        }
        return ResponseEntity.ok(new Message("Checkout failed"));
    }

    @Override
    public ResponseEntity<?> userHistory(HistoryInput historyInput, Integer start, Integer limit) {
        try {
            User user = userRepository.getUserByEmail(historyInput.getEmail());
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
}
