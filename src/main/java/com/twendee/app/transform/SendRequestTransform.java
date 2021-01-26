package com.twendee.app.transform;

import com.twendee.app.model.dto.SendRequestDTO;
import com.twendee.app.model.entity.AbsenceOutside;
import com.twendee.app.model.entity.Request;

import java.util.Date;

public class SendRequestTransform {
    public Request apply(SendRequestDTO sendRequestDTO){
        Date a = new Date();
        Request request = new Request();
        request.setTimeRequest(a);
        request.setReason(sendRequestDTO.getReason());
//        request.setUser(sendRequestDTO.getUser());

        AbsenceOutside outside = new AbsenceOutside();
        outside.setType(sendRequestDTO.getAbsenceOutside().isType());
        outside.setStart_date(sendRequestDTO.getAbsenceOutside().getStart_date());
        outside.setEnd_date(sendRequestDTO.getAbsenceOutside().getEnd_date());
        request.setAbsenceOutside(outside);
        return request;
    }
}
