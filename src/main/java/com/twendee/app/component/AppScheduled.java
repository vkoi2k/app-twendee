package com.twendee.app.component;


import com.twendee.app.model.dto.Message;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    @Scheduled(cron = "0 0 17 * * *")
    public void AutoCreatTimekeeping() {
        try {
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date2=new Date(new Date().getTime()+7*3600*1000);
            Date date1=DateToString.parse(DateToString.format(date2));

            //kiểm tra nếu hôm nay là thứ 7, CN thì thôi
            Calendar cal=Calendar.getInstance();
            cal.setTime(date1);
            if(cal.get(Calendar.DAY_OF_WEEK)==1 || cal.get(Calendar.DAY_OF_WEEK)==7) return;

            //lấy về list timeKeeping ngày hôm nay
            List<TimeKeeping> list = timeKeepingRepository.findByDateGreaterThanEqualAndDateLessThanEqual(
                    StringToDate.parse(DateToString.format(date1) + " 00:00:00"),
                    StringToDate.parse(DateToString.format(date1) + " 23:59:59")
            );
            //lấy về list userId đã đc tạo timeKeeping cho ngày hôm nay
            List<Integer> userIds = new ArrayList<>();
            for (TimeKeeping tk : list) {
                System.out.println("tk: " + tk.getUser().getUserId());
                userIds.add(tk.getUser().getUserId());
            }
            //lấy về list user chưa đc tạo timeKeeping cho ngày hôm nay
            List<User> users;
            if(userIds.size()<1) users=userRepository.findByDeletedFalse(Sort.by("name"));
            else users = userRepository.findByUserIdNotInAndDeletedFalse(userIds);

            //tạo timekeeping cho những user chưa đc tạo timekeeping cho ngày hôm nay
            for (User user : users) {
                TimeKeeping timeKeeping = new TimeKeeping();
                timeKeeping.setUser(user);
                timeKeeping.setDate(date1);
                timeKeepingRepository.save(timeKeeping);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
