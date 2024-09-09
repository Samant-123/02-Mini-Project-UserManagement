package com.sachin.binding;

import lombok.Data;

@Data
public class ResetPwdDTO 
{
	private String email;
	
	private String oldPwd;
	
	private String newPwd;
	
	private String confirmPwd;

}
