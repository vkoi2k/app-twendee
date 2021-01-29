package com.twendee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class InputUserDTO {
    private String name;
    private String cardId;
    private long birthday;
    private String email;
    private String pass;
    private String address;
    private String phone;
    private String avatar;
    //private boolean role;
    private boolean isVip;
    private String position;
    private boolean gender;
}
