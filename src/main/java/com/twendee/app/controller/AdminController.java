package com.twendee.app.controller;

import com.twendee.app.model.dto.Message;
import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    final
    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer id) {
        return ResponseEntity.ok(new Message(adminService.acceptRequest(id)));
    }

    @GetMapping("/refuse/{id}")
    public ResponseEntity<?> refuseRequest(@PathVariable Integer id) {
        return ResponseEntity.ok(new Message(adminService.refuseRequest(id)));
    }

    @GetMapping(value = "/time-keepings", params = "date")
    public List<TimeKeepingDTO> getListTimekeepingByDate
            (@RequestParam long date,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer limit) {
        return adminService.getListTimekeepingByDate(page, limit, date);
    }

    @GetMapping(value = "/time-keepings", params = "month")
    public List<TimeKeepingDTO> getListTimekeepingByMonth
            (@RequestParam Integer month,
             @RequestParam Integer year,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer limit) {
        return adminService.getListTimekeepingByMonth(page, limit, month, year);
    }

    //api tạo ra timeKeeping theo ngày để test
    //nhập vào ngày dạng long (millisecond)
    //test xong xóa đoạn code này đi
    @Autowired
    TimeKeepingRepository timeKeepingRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping(value = "/create-timekeepings", params = "date")
    public ResponseEntity<Message> createTimeKeepings(@RequestParam long date){
        Date date2=new Date(date+7*3600*1000);
        try {
            SimpleDateFormat DateToString = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date1=DateToString.parse(DateToString.format(date2));
            //lấy về list user chưa đc tạo timeKeeping cho ngày hôm nay
            List<TimeKeeping> list = timeKeepingRepository.findByDateGreaterThanEqualAndDateLessThanEqual(
                    StringToDate.parse(DateToString.format(date1) + " 00:00:00"),
                    StringToDate.parse(DateToString.format(date1) + " 23:59:59")
            );
            List<Integer> userIds = new ArrayList<>();
            for (TimeKeeping tk : list) {
                System.out.println("tk: " + tk.getUser().getUserId());
                userIds.add(tk.getUser().getUserId());
            }
            List<User> users;
            if(userIds.size()<1) users=userRepository.findByDeletedFalse(Sort.by("name"));
            else users = userRepository.findByUserIdNotInAndDeletedFalse(userIds);
            for (User user : users) {
                TimeKeeping timeKeeping = new TimeKeeping();
                timeKeeping.setUser(user);
                timeKeeping.setDate(date1);
                timeKeepingRepository.save(timeKeeping);
            }
            return ResponseEntity.ok(new Message("create timekeeping successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new Message("create timekeeping failed"));
        }
    }
    @GetMapping("/test-time")
    public String testTime(){
        SimpleDateFormat StringToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return StringToDate.format(new Date());
    }

}
