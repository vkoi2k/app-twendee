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

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public List<RequestDTO> findAll(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit) {
        return requestService.findAll(page, limit);
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        return requestService.findById(id);
    }

    @GetMapping(value = "/requests", params = "isAccept")
    public List<RequestDTO> findByIsAccept(@RequestParam Boolean isAccept,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "limit", required = false) Integer limit) {
        if (isAccept == true) {
            return requestService.findByIsAcceptTrue(page, limit);
        } else {
            return requestService.findByIsAcceptFalse(page, limit);

        }
    }

    @GetMapping(value = "/requests1", params = "type")
    public List<RequestDTO> findByType(@RequestParam String type,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "limit", required = false) Integer limit) {
        return  requestService.findByType(type,page,limit);


    }

    @GetMapping(value = "/requests/time", params = "date")
    public List<RequestDTO> getListRequestByDate
            (@RequestParam long date,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer limit) {
        return requestService.getListRequestByDate(page, limit, date);
    }



}
