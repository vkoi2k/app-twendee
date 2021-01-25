package com.twendee.app.component;




import com.twendee.app.model.entity.BaseEntity;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


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
    @Scheduled(cron = "0 * * * * *")
    public void AutoCreatTimekeeping(){
        //lấy về list user chưa đc tạo timeKeeping cho ngày hôm nay
        List<User> users= userRepository.getUserNotIn(new Date());
        for(User user:users){
            TimeKeeping timeKeeping=new TimeKeeping();
            timeKeeping.setUser(user);
            timeKeeping.setDate(new Date());
        }
    }

}
