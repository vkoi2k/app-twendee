package com.twendee.app.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileInput {
    private String name;
    private String cardId;
    private String birthday;
    private String address;
    private String phone;
    private String avatar;
    private boolean isVip;
    private String position;
    private boolean gender;

}
