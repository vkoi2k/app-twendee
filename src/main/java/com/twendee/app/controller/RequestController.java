package com.twendee.app.controller;

import com.twendee.app.model.dto.Message;
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
    public List<RequestDTO> findByIsAcceptAndType(@RequestParam(value = "page", required = false) Integer page,
                                                  @RequestParam(value = "limit", required = false) Integer limit,
                                                  @RequestParam(value = "isAccepted", required = false) Boolean isAccepted,
                                                  @RequestParam(value = "type", required = false) String type) {
        if (isAccepted != null && type == null) {
            if (isAccepted == true) {
                return requestService.findByIsAcceptTrue(page, limit);
            } else {
                return requestService.findByIsAcceptFalse(page, limit);
            }
        } else if (type != null && isAccepted == null) {
            return requestService.findByType(type, page, limit);
        } else if (type != null && isAccepted != null) {
            return requestService.findByIsAcceptAndType(isAccepted, type, page, limit);

        } else {
            return requestService.findAll(page, limit);
        }
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        return requestService.findById(id);
    }

//    @GetMapping(value = "/requests", params = "isAccept")
//    public List<RequestDTO> findByIsAccept(@RequestParam Boolean isAccept,
//                                           @RequestParam(value = "page", required = false) Integer page,
//                                           @RequestParam(value = "limit", required = false) Integer limit) {
//        if (isAccept == true) {
//            return requestService.findByIsAcceptTrue(page, limit);
//        } else {
//            return requestService.findByIsAcceptFalse(page, limit);
//
//        }
//    }
//
//    @GetMapping(value = "/requests1", params = "type")
//    public List<RequestDTO> findByType(@RequestParam String type,
//                                           @RequestParam(value = "page", required = false) Integer page,
//                                           @RequestParam(value = "limit", required = false) Integer limit) {
//        return  requestService.findByType(type,page,limit);
//
//
//    }

    @GetMapping(value = "/requests/time")
    public List<RequestDTO> getListRequestByDate
            (@RequestParam(value = "dateMin") Long dateMin,
             @RequestParam(value = "dateMax", required = false) Long dateMax,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer limit) {
       if (dateMax == null){
           return requestService.getListRequestByDate(page, limit, dateMin, dateMin);
       }
       else{
           return requestService.getListRequestByDate(page, limit, dateMin, dateMax);
       }

    }

    @DeleteMapping("/requests/{id}")
    public Message delete(@PathVariable Integer id){
        return requestService.delete(id);
    }

    @GetMapping(value = "/requests", params = "search")
    public List<RequestDTO> searchRequest(@RequestParam String search,
                                          @RequestParam(required = false) Integer page,
                                          @RequestParam(required = false) Integer limit){
        return requestService.searchRequest(search, page,limit);
    }


}
