package com.twendee.app;

import com.twendee.app.model.dto.UserDTO;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import com.twendee.app.reponsitory.TimeKeepingRepository;
import com.twendee.app.reponsitory.UserRepository;
import com.twendee.app.service.StaffService;
import com.twendee.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
        List<User> users = userRepository.findByDeletedFalse(Sort.by("name"));

//tạo resource + thêm link
//        EntityModel<User> resource = EntityModel.of(user);
//        resource.add(linkTo.withRel("all-users"));
////lấy link (set thế này thì khi thay đổi uri của method thì link này cũng đổi theo)
//        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).tenMethod());    //class.method
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo



    }

}
