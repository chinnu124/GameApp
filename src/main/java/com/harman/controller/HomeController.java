package com.harman.controller;



import com.harman.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController{


	@Autowired
	CharacterService characterService;


	@GetMapping("/character/{characterName}")
	public ResponseEntity getMaxPower(@PathVariable String characterName){
		Integer maxPower =  characterService.getMAxPower(characterName);
		if(maxPower == null) return  ResponseEntity.ok("No data found");
		return ResponseEntity.ok(maxPower);
	}
	
	
		
}
