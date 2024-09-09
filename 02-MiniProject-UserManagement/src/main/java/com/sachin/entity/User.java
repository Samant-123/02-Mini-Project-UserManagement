package com.sachin.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "User_Master")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String userName;
	
	private String email;
	
	private String pwd;
	
	private String pwdUpdated;
	
	private Long phnno;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;
	

	@ManyToOne
	@JoinColumn(name="state_id")
	private State state;
	

	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate createdTimestamp;
	@CreationTimestamp
	@Column(insertable  = false)
	private LocalDate updatedTimestamp;
}
