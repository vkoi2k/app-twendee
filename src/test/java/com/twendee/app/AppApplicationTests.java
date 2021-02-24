package com.twendee.app;

import com.twendee.app.component.MailSender;
import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.RequestRepository;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class AppApplicationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TimeKeepingRepository timeKeepingRepository;

    @Autowired
    MailSender mailSender;

    @Autowired
    StaffService staffService;

    @Autowired
    RequestRepository requestRepository;

    @Test
    void testDayOfWeek() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = simpleDateFormat.parse("14/02/2021");
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            System.out.println(cal.toString());
            System.out.println("ngay: "+cal.getTime().toString());
            System.out.println("thu: " + cal.get(Calendar.DAY_OF_WEEK));
            ;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Test
    void linhTinh(){
        mailSender.send("sieunhanbay1997@gmail.com", "ahihi", "hello mail sender");
    }

    @Test
    void testList(){
        Optional<Request> optionalRequest = requestRepository.findById(1);
        Request request=optionalRequest.get();
        TimeKeeping timeKeeping =new TimeKeeping();
        timeKeeping.setUser(request.getUser());
        timeKeeping.setDate(new Date());
        timeKeeping.setRequest(request);
        timeKeepingRepository.save(timeKeeping);
    }

    public Date removeTime(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String stringDate=simpleDateFormat.format(date);
        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
}
