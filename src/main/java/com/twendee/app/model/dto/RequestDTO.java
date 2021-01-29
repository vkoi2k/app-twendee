package com.twendee.app.model.dto;

import com.twendee.app.model.entity.Request;
import com.twendee.app.model.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestDTO {
    private Integer requestId;
    private Date timeRequest;
    private String reason;
    private boolean isAccept;
    private String type;
//    private String email;
//
//    public RequestDTO(){}
//    public RequestDTO(Request request){
//        this.requestId = request.getRequestId();
//        this.timeRequest = request.getTimeRequest();
//        this.reason = request.getReason();
//        this.isAccept = request.isAccept();
//        this.type = request.type;
//    }
}
