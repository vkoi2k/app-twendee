package com.twendee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twendee.app.model.entity.Request;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SendRequestAbsenceOutsideDTO{
    private String reason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date endDate;
    private boolean type;
    private String email;

    public SendRequestAbsenceOutsideDTO(){}

    public SendRequestAbsenceOutsideDTO(Request request){
        this.email = request.getUser().getEmail();
        this.reason = request.getReason();
        this.startDate = request.getAbsenceOutside().getStartDate();
        this.endDate = request.getAbsenceOutside().getEndDate();
        this.type = request.getAbsenceOutside().isType();
    }
}
