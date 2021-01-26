package com.twendee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twendee.app.model.entity.TimeKeeping;
import com.twendee.app.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class TimeKeepingDTO {
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date checkin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Date checkout;

    //số phút đến muộn
    private int timeLate;
    //số phút về sớm
    private int timeEarly;
    public TimeKeepingDTO(TimeKeeping timeKeeping) throws ParseException {
        this.user=timeKeeping.getUser();
        this.date=timeKeeping.getDate();
        this.checkin=timeKeeping.getCheckin();
        this.checkout=timeKeeping.getCheckout();

        SimpleDateFormat DateToString=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat StringToDate=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //tính số phút đến muộn
        long late=timeKeeping.getCheckin().getTime()-
                StringToDate.parse(DateToString.format(timeKeeping.getDate())+" 09:00:00").getTime();
        if(late>0){
            this.timeLate=Math.round(late/60000);
        }else this.timeLate=0;

        //tính số phút về sớm
        long early=-timeKeeping.getCheckout().getTime()+
                StringToDate.parse(DateToString.format(timeKeeping.getDate())+" 18:00:00").getTime();
        if(early>0){
            this.timeEarly=Math.round(early/60000);
        }else this.timeEarly=0;
    }
}
