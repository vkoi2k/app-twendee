package com.twendee.app.component;


import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class AppScheduled {
    final
    UserRepository userRepository;

    final
    TimeKeepingRepository timeKeepingRepository;

    @Autowired
    public AppScheduled(UserRepository userRepository, TimeKeepingRepository timeKeepingRepository) {
        this.userRepository = userRepository;
        this.timeKeepingRepository = timeKeepingRepository;
    }

    //tự động tạo các bản ghi timeKeeping cho tất cả nhân viên vào 00:00:00 mỗi ngày
    @Scheduled(cron = "0 0 0 * * *")
    public void AutoCreatTimekeeping() {
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
            List<User> users = userRepository.findByUserIdNotIn(userIds);
            for (User user : users) {
                TimeKeeping timeKeeping = new TimeKeeping();
                timeKeeping.setUser(user);
                timeKeeping.setDate(new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
