package com.sachin.binding;

import lombok.Data;

@Data
public class RegisterFormDTO 
{
	private Integer userId;
	private String userName;
	
	private String email;
	
	private String pwd;
	
	private String pwdUpdated;
	
	private Long phnno;
	
	private Integer countryId;
	
	private Integer stateId;
	
	private Integer cityId;

}
