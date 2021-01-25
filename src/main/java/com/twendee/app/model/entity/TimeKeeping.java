package com.twendee.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity(name = "timekeeping")
@Getter
@Setter
public class TimeKeeping extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_keeping_id")
    public Integer timeKeepingId;

    @Column(nullable = false, unique = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+07")
            private Date date;

    @Column(nullable = false)
    private Date checkin;

    @Column(nullable = false)
            private Date checkout;


    @ManyToOne()
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
