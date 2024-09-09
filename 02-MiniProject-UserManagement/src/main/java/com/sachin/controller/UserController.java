package com.sachin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sachin.binding.LogInDTO;
import com.sachin.binding.QuoteApiResponseDTO;
import com.sachin.binding.RegisterFormDTO;
import com.sachin.binding.ResetPwdDTO;
import com.sachin.binding.UserDTO;
import com.sachin.service.DashBoardService;
import com.sachin.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
							
	@Autowired
	private DashBoardService dssBoardService;

	@GetMapping("/register")
	public String loadRegisterPage(Model model) {
		Map<Integer, String> countriesMapDto = userService.getcountries();
		model.addAttribute("countriesMap", countriesMapDto);

		RegisterFormDTO registerFormDTO = new RegisterFormDTO();
		model.addAttribute("registerForm", registerFormDTO);

		return "register";

	}

	@ResponseBody
	@GetMapping("/states/{countryId}")
	public Map<Integer, String> getStates(@PathVariable Integer countryId) {
		Map<Integer, String> stateMap = userService.getstate(countryId);

		return stateMap;

	}

	@ResponseBody
	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> getCities(@PathVariable Integer stateId) {
		Map<Integer, String> cityMap = userService.getcities(stateId);

		return cityMap;

	}

	@PostMapping("/register")
	public String handleRegistration(@ModelAttribute("registerForm") RegisterFormDTO registerFormDTO, Model model) {
		boolean emailCheck = userService.duplicateEmailCheck(registerFormDTO.getEmail());
		Map<Integer, String> countriesMapDto = userService.getcountries();
		model.addAttribute("countriesMap", countriesMapDto);
		if (emailCheck) {
			model.addAttribute("emsg", "Duplicate Email Found");
		model.addAttribute("registerForm", new RegisterFormDTO());
		}
		else {
			boolean saveUser = userService.saveUser(registerFormDTO);
			if (saveUser) {
				// User Saved
				model.addAttribute("smsg", "User Registration Successfully!!!, Pls Check Your E-Mail!!!");
			} else {
				// failed to save
				model.addAttribute("emsg", "resistration Failed!!!");
			}
		}
		
		model.addAttribute("countries", userService.getClass());
		return "register";

	}

	@GetMapping("/")
	public String logInPage(Model model) {
		LogInDTO logInDTO = new LogInDTO();
		model.addAttribute("logInform", logInDTO);
		return "index";

	}

	@PostMapping("/logIn")
	public String handleLoginFrmBtn(@ModelAttribute("logInform") LogInDTO logInDTO, Model model) {
		UserDTO userdto = userService.login(logInDTO);
		if (userdto == null)
			model.addAttribute("emsg", "Invalid Credentials");
		else {
			String pwdUpdated = userdto.getPwdUpdated();
			if (pwdUpdated.equalsIgnoreCase("Yes")) {
				// display Dashboard

				return "redirect:dashboard";
			} else {
				// display reset password
				return "redirect:reset-pwd-page?email=" + userdto.getEmail();
			}
		}
		return "index";

	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		QuoteApiResponseDTO quoteApiResp = dssBoardService.getQuote();
		model.addAttribute("quote", quoteApiResp);
		return "dashboard";
	}

	@GetMapping("/reset-pwd-page")
	public String loadPwdResetPage(@RequestParam String email, Model model) {
		ResetPwdDTO resetPwdDTO = new ResetPwdDTO();
		resetPwdDTO.setEmail(email);
		model.addAttribute("resetPwd", resetPwdDTO);

		return "resetpwd";
	}

	@PostMapping("/resetpwd")
	public String handlePwdReset(ResetPwdDTO resetPwdDTO, Model model) {
		boolean resetPwd = userService.resetPwd(resetPwdDTO);
		if (resetPwd)
			return "redirect:dashboard";
		return "resetpwd";
	}
}
