package com.sachin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sachin.entity.City;

public interface CityRepo extends JpaRepository<City,Integer>
{
	public List<City> findBystateId (Integer stateId);

}
