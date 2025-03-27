package com.example.UserService.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_info", schema = "phoenix")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "birthdate", length = 50)
    private String birthdate;

    @Column(name = "disable_status")
    private Boolean disableStatus;

    @Column(name = "married_status")
    private Boolean marriedStatus;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_by", length = 50)
    private String updateBy;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_by", length = 50)
    private String createBy;

    @Column(name = "status_active")
    private Boolean statusActive;
}
