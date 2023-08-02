package com.nguyenphitan.BeetechLogin.domain.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Entity
@Data
public class RefreshToken {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String token;

    private Instant expiryDate;
    
    @Value("${jwt.active.default}")
    private Boolean isActive;

    public RefreshToken() {
    }

}
