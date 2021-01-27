package com.twendee.app.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class SendRequestLateEarlyDTO extends SendRequestDTO{
    private Date date;
    private int time_late;
    private int time_early;
}
