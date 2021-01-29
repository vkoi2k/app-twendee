package com.twendee.app.controller;

import com.twendee.app.model.dto.RequestDTO;
import com.twendee.app.model.entity.Request;
import com.twendee.app.service.RequestService;
import org.springframework.http.ResponseEntity;
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
    public List<RequestDTO> findAll(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit){
        return requestService.findAll(page,limit);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id){
        return requestService.findById(id);
    }

}
