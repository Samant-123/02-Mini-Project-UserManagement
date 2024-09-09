package com.sachin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sachin.entity.State;

public interface StateRepo extends JpaRepository<State,Integer>
{
	//Select * from State where countryId=:contryId
	public List<State> findBycountryId(Integer countryId);

}
