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
    private String email;

    public RequestDTO(){}
    public RequestDTO(Request request){
        this.requestId = request.getRequestId();
        this.timeRequest = request.getTimeRequest();
        this.reason = request.getReason();
        this.isAccept = request.isAccept();
        if (request.getLateEarly()!=null&&request.getAbsenceOutside()==null&&request.getCheckoutSupport()==null){
            this.type = "Đi muộn - Về sớm";
        }if (request.getLateEarly()==null&&request.getAbsenceOutside()!=null&&request.getCheckoutSupport()==null){
            this.type = "Xin nghỉ - Out side";
        }if (request.getLateEarly()==null&&request.getAbsenceOutside()==null&&request.getCheckoutSupport()!=null){
            this.type = "Quên check out";
        }
        this.email = request.getUser().getEmail();
    }
}
