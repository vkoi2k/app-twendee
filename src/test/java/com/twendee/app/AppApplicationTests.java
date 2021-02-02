package com.twendee.app;

import com.twendee.app.component.MailSender;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        mailSender.send("sieunhanbay1997@gmail.com", "ahihi", "hello mail sender");
    }

    @Test
    void testList(){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date endDate = simpleDateFormat.parse("25/02/2021");
            Date date;
            for (date = removeTime(new Date(new Date().getTime()+86_400_000)); date.before(removeTime(endDate)) || date.equals(removeTime(endDate));
                 date.setTime(date.getTime() + 86_400_000)) {
                System.out.println(simpleDateFormat.format(date));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
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
