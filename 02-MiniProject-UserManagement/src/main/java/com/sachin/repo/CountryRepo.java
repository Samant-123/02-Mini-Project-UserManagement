package com.sachin.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.sachin.entity.Country;

public interface CountryRepo extends JpaRepository<Country,Integer>
{
	

}
