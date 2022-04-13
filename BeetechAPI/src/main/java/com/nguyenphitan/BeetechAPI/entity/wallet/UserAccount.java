package com.nguyenphitan.BeetechAPI.entity.wallet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "userAccounts")
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private Long userId;

	@NotBlank
	private String name;

	@NotBlank
	private String email;
	
	@NotBlank
	private String phoneNumber;
	
	@NotBlank
	private String role;
}
