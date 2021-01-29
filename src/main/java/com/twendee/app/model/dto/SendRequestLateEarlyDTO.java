package com.twendee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twendee.app.model.entity.Request;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SendRequestLateEarlyDTO{
    private String reason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
    private int timeLate;
    private int timeEarly;
    private String email;
    public SendRequestLateEarlyDTO(){}
    public SendRequestLateEarlyDTO(Request request){
        this.reason = request.getReason();
        this.date = request.getLateEarly().getDate();
        this.timeEarly = request.getLateEarly().getTimeEarly();
        this.timeLate = request.getLateEarly().getTimeLate();
    }
}
