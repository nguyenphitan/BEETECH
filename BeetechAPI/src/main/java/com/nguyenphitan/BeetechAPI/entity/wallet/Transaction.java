package com.nguyenphitan.BeetechAPI.entity.wallet;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction")
@AllArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long senderId;	// Người gửi
		
	private Long receiverId;	// Người nhận

	private Double amount;	// Số tiền 
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private String status;
	
}
