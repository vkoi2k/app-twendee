package com.twendee.app.model.dto;

import com.twendee.app.model.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class RequestDTO {
    private Integer requestId;
    private Date timeRequest;
    private String reason;
    private boolean isAccept;
    private String type;
//    private User user;
}
