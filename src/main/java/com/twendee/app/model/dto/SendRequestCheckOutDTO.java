package com.twendee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twendee.app.model.entity.Request;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SendRequestCheckOutDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private long date;
    private String email;
}
