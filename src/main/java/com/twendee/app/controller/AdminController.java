package com.twendee.app.controller;

import com.twendee.app.model.dto.TimeKeepingDTO;
import com.twendee.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (adminService.acceptRequest(id)) {
            return ResponseEntity.ok("accept_successfully");
        } else {
            return ResponseEntity.ok("accept_false");
        }
    }

    @GetMapping("/refuse/{id}")
    public ResponseEntity<?> refuseRequest(@PathVariable Integer id) {
        if (adminService.refuseRequest(id)) {
            return ResponseEntity.ok("refuse_successfully");
        } else {
            return ResponseEntity.ok("refuse_false");
        }
    }

    @GetMapping(value = "/admin/time-keepings", params = "date")
    public List<TimeKeepingDTO> getListTimekeepingByDate
            (@RequestParam long date,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer limit) {
        return adminService.getListTimekeepingByDate(page, limit, date);
    }


}
