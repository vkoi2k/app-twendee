package com.twendee.app.model.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputProfileDTO {
    private String name;
    private String cardId;
    private long birthday;
    private String email;
    private String pass;
    private String address;
    private String phone;
    private String avatar;
    private boolean isVip;
    private String position;
    private boolean gender;
}
