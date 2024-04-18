package com.etaskify.etaskify.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "forgot_passwords")
public class ForgotPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp", nullable = false)
    private Integer otp;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @OneToOne
    private UserEntity user;

}
