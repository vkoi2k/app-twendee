package com.twendee.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;


@Entity(name = "absence_outside")
@Getter
@Setter
public class AbsenceOutside extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "absence_outside_id")
    private Integer absenceOutsideId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date start_date;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date end_date;

    //absence (xin nghỉ) là true
    //outside là false
    @Column(nullable = false)
    private boolean type;
}
