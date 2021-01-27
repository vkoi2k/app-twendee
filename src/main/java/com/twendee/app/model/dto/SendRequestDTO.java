package com.twendee.app.model.dto;

import com.twendee.app.model.entity.AbsenceOutside;
import com.twendee.app.model.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Data
@Getter
@Setter
public class SendRequestDTO {
    private Date timeRequest;
    private String reason;
//    private User user;
}
