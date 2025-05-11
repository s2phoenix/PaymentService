package com.example.POCBankService.entity;
import com.example.POCBankService.model.enumconstant.UserRole;
import com.example.POCBankService.model.enumconstant.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_info", schema = "reborn")
@Getter
@Setter
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thai_name")
    private String thaiName;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "email")
    private String email;

    @Column(name = "pin")
    private String pin;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "account")
    private String account;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "create_by")
    private String createBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
}
