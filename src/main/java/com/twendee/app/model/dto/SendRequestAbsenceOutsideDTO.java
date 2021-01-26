package com.twendee.app.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SendRequestAbsenceOutsideDTO extends SendRequestDTO{
    private Date start_date;
    private Date end_date;
}
