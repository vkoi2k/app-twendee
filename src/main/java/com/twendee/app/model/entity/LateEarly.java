package com.twendee.app.model.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity(name = "late_early")
@Getter
@Setter
public class LateEarly extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "late_early_id")
    private Integer lateEarlyId;


    //ngày muốn xin đến muộn/về sớm
    @Column(nullable = false)
    private Date date;

    //số phút muốn xin đến muộn/về sớm
    @Column(nullable = false)
    private int time;

    //xin đến muộn là false
    //xin về sớm là true
    @Column(nullable = false)
    private boolean type;
}
