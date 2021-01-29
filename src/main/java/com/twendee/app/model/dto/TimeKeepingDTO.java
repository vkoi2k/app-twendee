package com.twendee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twendee.app.model.entity.TimeKeeping;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class TimeKeepingDTO {
    private String username;

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

    //ghi chú
    private String note;

    public TimeKeepingDTO(TimeKeeping timeKeeping) throws ParseException {
        this.username=timeKeeping.getUser().getName();
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

        if(timeKeeping.getRequest() !=null){
            if(timeKeeping.getRequest().getLateEarly()!=null){
                this.note="Xin đi muộn/về sớm";
            }else if(timeKeeping.getRequest().getAbsenceOutside()!=null){
                if(timeKeeping.getRequest().getAbsenceOutside().isType())
                    this.note="Xin nghỉ";
                else this.note="Xin đi outside";
            }else if(timeKeeping.getRequest().getCheckoutSupport()!=null){
                    this.note="Checkout bù";
            }
        }
    }
}
