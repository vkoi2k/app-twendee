package com.twendee.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;

@Data
@Entity(name = "user")
@Getter
@Setter
@EnableAutoConfiguration
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pass;
    private String cardId;

    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dob;

    @Column(nullable = true)
    private String address;

    //mặc định sẽ là đường dẫn tới noImage.jpg
    @Column(nullable = false)
    private String avatar;

    //admin là true
    //user là false
    @Column(nullable = false)
    private boolean role;

    //unique
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private boolean isVip;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<TimeKeeping> timeKeepings;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Request> requests;
}
