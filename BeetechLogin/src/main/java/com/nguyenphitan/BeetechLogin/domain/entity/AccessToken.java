package com.nguyenphitan.BeetechLogin.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "access_token")
@NoArgsConstructor
public class AccessToken {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String token;
    
    public AccessToken(Long userId, String token) {
    	this.userId = userId;
    	this.token = token;
    }
}
