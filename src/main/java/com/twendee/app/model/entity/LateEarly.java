package com.twendee.app.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
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
public class LateEarly extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "late_early_id")
    private Integer lateEarlyId;


    //ngày muốn xin đến muộn/về sớm
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date date;

    //số phút muốn xin đến muộn
    @Column(nullable = false)
    private int time_late = 0;

    //số phút muốn xin về sớm
    @Column(nullable = false)
    private int time_early = 0;
}
