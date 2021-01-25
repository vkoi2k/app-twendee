package com.twendee.app.model.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "checkout_support")
@Getter
@Setter
public class CheckoutSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkout_support_id")
    private Integer checkoutSupportId;

    //xin checkout hộ ngày
    @Column(nullable = false)
    private Date date;
}
