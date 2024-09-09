package com.sachin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sachin.entity.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer>
{
	public  User  findByEmailAndPwd(String email, String pwd) ;
	
	public  User findByEmail(String email);
	//public User findByEmail(String Email);
	

}
