package com.sachin.service;

import java.util.Map;

import com.sachin.binding.LogInDTO;
import com.sachin.binding.RegisterFormDTO;
import com.sachin.binding.ResetPwdDTO;
import com.sachin.binding.UserDTO;

public interface UserService 
{
	public Map<Integer ,String> getcountries();
	
	public Map<Integer,String> getstate(Integer countryId);
	
	public Map<Integer,String> getcities(Integer stateId);
	
	public boolean duplicateEmailCheck(String email);
	public boolean saveUser(RegisterFormDTO registerFormDTO);
	
	public UserDTO login(LogInDTO logInDTO);
	
	public boolean resetPwd(ResetPwdDTO resetPwdDTO);
	

}
