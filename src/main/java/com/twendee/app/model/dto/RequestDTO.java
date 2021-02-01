package com.twendee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date endDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
    private int timeLate;
    private int timeEarly;
    private String email;

    public RequestDTO(){}
    public RequestDTO(Request request){
        this.requestId = request.getRequestId();
        this.timeRequest = request.getTimeRequest();
        this.reason = request.getReason();
        this.isAccept = request.isAccept();
        if (request.getLateEarly()!=null&&request.getAbsenceOutside()==null&&request.getCheckoutSupport()==null){
            this.type = "Đi muộn - Về sớm";
            this.date = request.getLateEarly().getDate();
            this.timeLate = request.getLateEarly().getTimeLate();
            this.timeEarly = request.getLateEarly().getTimeEarly();
        }if (request.getLateEarly()==null&&request.getAbsenceOutside()!=null&&request.getCheckoutSupport()==null){
            this.type = "Xin nghỉ - Out side";
            this.startDate = request.getAbsenceOutside().getStartDate();
            this.endDate = request.getAbsenceOutside().getEndDate();
        }if (request.getLateEarly()==null&&request.getAbsenceOutside()==null&&request.getCheckoutSupport()!=null){
            this.type = "Quên check out";
            this.date = request.getCheckoutSupport().getDate();
        }
        this.email = request.getUser().getEmail();
    }
}
