package com.sachin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sachin.binding.LogInDTO;
import com.sachin.binding.RegisterFormDTO;
import com.sachin.binding.ResetPwdDTO;
import com.sachin.binding.UserDTO;
import com.sachin.entity.City;
import com.sachin.entity.Country;
import com.sachin.entity.State;
import com.sachin.entity.User;
import com.sachin.repo.CityRepo;
import com.sachin.repo.CountryRepo;
import com.sachin.repo.StateRepo;
import com.sachin.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailService emailService;

	// Get All Countries From Database
	@Override
	public Map<Integer, String> getcountries() {
		Map<Integer, String> countryMap = new HashMap<>();
		List<Country> countryList = countryRepo.findAll();
		countryList.forEach(c -> {
			countryMap.put(c.getCountryId(), c.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getstate(Integer countryId) {
		Map<Integer, String> stateMap = new HashMap<>();
		List<State> stateList = stateRepo.findBycountryId(countryId);
		stateList.forEach(s -> {
			stateMap.put(s.getStateId(), s.getStateName());
		});
		return stateMap;
	}

	@Override
	public Map<Integer, String> getcities(Integer stateId) {
		Map<Integer, String> cityMap = new HashMap<>();
		List<City> cityList = cityRepo.findBystateId(stateId);
		cityList.forEach(city -> cityMap.put(city.getCityId(), city.getCityName()));
		return cityMap;
	}

	@Override
	public boolean duplicateEmailCheck(String email) {
		User byEmail = userRepo.findByEmail(email);
		if (byEmail != null)
			return true;
		return false;
	}

	public boolean saveUser(RegisterFormDTO registerFormDTO) {
		User userEntity = new User();
		// Copying registerForm Data with userEntity
		BeanUtils.copyProperties(registerFormDTO, userEntity);
		// Set the Value with userEntity country variable
		Country country = countryRepo.findById(registerFormDTO.getCountryId())
				.orElseThrow(() -> new RuntimeException("Country Is not Present"));
		userEntity.setCountry(country);
		// Set the Value with userEntity state variable
		State state = stateRepo.findById(registerFormDTO.getStateId())
				.orElseThrow(() -> new RuntimeException("State Is not Present"));
		userEntity.setState(state);
		// Set the Value with userEntity state variable
		City city = cityRepo.findById(registerFormDTO.getCityId())
				.orElseThrow(() -> new RuntimeException("City Is not Present"));
		userEntity.setCity(city);
		String generateRandomPwd = generateRandomPwd();
		userEntity.setPwd(generateRandomPwd);
		userEntity.setPwdUpdated("No");

		User saveUser = userRepo.save(userEntity);
		if (saveUser != null) {

			String subject = "Your Account is Created";
			String body = "Your Password to LogIn: " + generateRandomPwd;
			String to = registerFormDTO.getEmail();
			return emailService.sendEmail(subject, body, to);
		}
		return false;
	}

	@Override
	public UserDTO login(LogInDTO logInDTO) {
		User userEntity = userRepo.findByEmailAndPwd(logInDTO.getEmail(), logInDTO.getPwd());
		if (userEntity != null) {
			UserDTO userdto = new UserDTO();
			BeanUtils.copyProperties(userEntity, userdto);
			return userdto;
		}
		return null;
	}

	@Override
	public boolean resetPwd(ResetPwdDTO resetPwdDTO) {
		String email = resetPwdDTO.getEmail();
		User entity = userRepo.findByEmail(email);
		// setting new pwd
		entity.setPwd(resetPwdDTO.getNewPwd());
		entity.setPwdUpdated("yes");
		userRepo.save(entity);
		return true;
	}

	private String generateRandomPwd() {
		String uppercaseLetter = "ASDFGHJKLPOIUYTREWQZXCVBNM";
		String lowercaseLetter = "asdfghjklpoiuytrewqzxcvbnm";

		String alphabets = uppercaseLetter + lowercaseLetter;
		Random random = new Random();
		StringBuffer generatedPwd = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			// give any number 0 to 51
			int randomIndex = random.nextInt(alphabets.length());
			generatedPwd.append(alphabets.charAt(randomIndex));
		}
		return generatedPwd.toString();
	}

}
