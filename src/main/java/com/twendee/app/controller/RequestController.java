package com.twendee.app.controller;

import com.twendee.app.model.entity.Request;
import com.twendee.app.service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class RequestController {

    private RequestService requestService;

    public RequestController(RequestService requestService){
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public List<Request> findAll(){
        return requestService.findAll();
    }

    @GetMapping("/requests/{id}")
    public Optional<Request> getDetail(@PathVariable Integer id){
        return requestService.findById(id);
    }

}
