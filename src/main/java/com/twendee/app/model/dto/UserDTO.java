package com.twendee.app.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class UserDTO {
    private String name;
    private String cardId;
    private Date dob;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String avatar;
}
