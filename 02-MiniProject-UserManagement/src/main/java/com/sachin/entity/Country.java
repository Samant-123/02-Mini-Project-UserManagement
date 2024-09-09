package com.sachin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Country_Master")
public class Country 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer countryId;

	private String countryName;
}
