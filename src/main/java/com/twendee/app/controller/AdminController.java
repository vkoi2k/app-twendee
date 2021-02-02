package com.twendee.app.controller;

import com.twendee.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    final
    AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<?> acceptRequest(@PathVariable Integer id){
        if(adminService.acceptRequest(id)){
            return ResponseEntity.ok("accept_successfully");
        }else{
            return ResponseEntity.ok("accept_false");
        }
    }

    @GetMapping("/refuse/{id}")
    public ResponseEntity<?> refuseRequest(@PathVariable Integer id){
        if(adminService.refuseRequest(id)){
            return ResponseEntity.ok("refuse_successfully");
        }else{
            return ResponseEntity.ok("refuse_false");
        }
    }

}
