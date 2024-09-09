package com.sachin.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sachin.binding.QuoteApiResponseDTO;

@Service
public class DashBoardServiceImpl implements DashBoardService {

	private String quoteApiUrl = "https://dummyjson.com/quotes/random";

	@Override
	public QuoteApiResponseDTO getQuote() {
		RestTemplate restTemplate = new RestTemplate();
		// Send HTTP get req and Store Response into ResponseDto Object
		ResponseEntity<QuoteApiResponseDTO> forEntity = restTemplate.getForEntity(quoteApiUrl,
				QuoteApiResponseDTO.class);
		QuoteApiResponseDTO body = forEntity.getBody();
		return body;
	}

}
