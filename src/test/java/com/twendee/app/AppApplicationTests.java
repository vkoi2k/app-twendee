package com.twendee.app;

import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class AppApplicationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TimeKeepingRepository timeKeepingRepository;

    @Test
    void contextLoads() {
        try {
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            System.out.println(StringToDate.parse(DateToString.format(date) + " 00:00:00").toString());
            System.out.println(StringToDate.parse(DateToString.format(date) + " 23:59:59").toString());
            //lấy về list user chưa đc tạo timeKeeping cho ngày hôm nay
            List<TimeKeeping> list = timeKeepingRepository.findByDateGreaterThanEqualAndDateLessThanEqual(
                    StringToDate.parse(DateToString.format(date) + " 00:00:00"),
                    StringToDate.parse(DateToString.format(date) + " 23:59:59")
            );
            List<Integer> userIds = new ArrayList<>();
            for (TimeKeeping tk : list) {
                System.out.println("tk: " + tk.getUser().getUserId());
                userIds.add(tk.getUser().getUserId());
            }
            List<User> users = userRepository.findByUserIdNotInAndDeletedFalse(userIds);
            for (User user : users) {
                System.out.println("id not in: "+user.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void linhTinh(){
        SimpleDateFormat DateToString = new SimpleDateFormat("01/MM/yyyy");
        SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println(DateToString.format(date) + " 00:00:00");
    }

}
