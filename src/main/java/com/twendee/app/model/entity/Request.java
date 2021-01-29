package com.twendee.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;

@Entity(name = "request")
@Getter
@Setter
public class Request extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;

    //thời gian gửi request
    @Column(nullable = false, name = "time_request")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date timeRequest;

    @Column(nullable = true)
    private String reason;

    @Column(nullable = true)
    private boolean isAccept;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "absence_outside_id")
    private AbsenceOutside absenceOutside;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "late_early_id")
    private LateEarly lateEarly;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "checkout_support_id")
    private CheckoutSupport checkoutSupport;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER)
    private Set<TimeKeeping> timeKeepings;
}
