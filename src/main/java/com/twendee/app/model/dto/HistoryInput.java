package com.twendee.app.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryInput {
    private String email;
    private int month;
    private int year;
}
