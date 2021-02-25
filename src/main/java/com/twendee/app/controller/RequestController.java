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
                                                  @RequestParam(value = "isAccepted",defaultValue = "-1") Integer isAccepted,
                                                  @RequestParam(value = "type",defaultValue = "0") String type) {
        if ( type.equals("0")) {
            switch (isAccepted){
                case 1:
                    return requestService.findByIsAcceptTrue(page, limit);

                case 0:
                    return requestService.findByIsAcceptFalse(page, limit);
                case 2:
                    return requestService.findByIsAcceptNull(page, limit);

                default:
                    return requestService.findAll(page,limit);


                }
        }

        else
            {
                switch (isAccepted){
                    case 1:
                        return requestService.findByIsAcceptAndType(isAccepted,type,page, limit);
                    case 0:
                        return requestService.findByIsAcceptAndType(isAccepted,type,page, limit);
                    case 2:
                        return requestService.findByIsAcceptAndType(isAccepted,type,page, limit);


                    default:
                        return requestService.findByType(type, page, limit);


                }


        }
//        else {
//            return requestService.findAll(page, limit);
//        }
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        return requestService.findById(id);
    }



    @GetMapping(value = "/requests/time")
    public List<RequestDTO> getListRequestByDate
            (@RequestParam(value = "dateMin") Long dateMin,
             @RequestParam(value = "dateMax", required = false) Long dateMax,
             @RequestParam(required = false) Integer page,
             @RequestParam(required = false) Integer limit) {
        if (dateMax == null) {
            return requestService.getListRequestByDate(page, limit, dateMin, dateMin);
        } else {
            return requestService.getListRequestByDate(page, limit, dateMin, dateMax);
        }

    }

    @DeleteMapping("/requests/{id}")
    public Message delete(@PathVariable Integer id) {
        return requestService.delete(id);
    }


}
