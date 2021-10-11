package com.harman.controller;

import com.harman.models.Characters;
import com.harman.service.CharacterService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MockyClient {

	Map<String, Integer> characterMap = new HashMap();
	Map<String, Characters> map = new HashMap();

	Logger logger = LoggerFactory.getLogger(MockyClient.class);

	@Async
	public CompletableFuture<JSONArray> getAvengers() {
		String url = "http://www.mocky.io/v2/5ecfd5dc3200006200e3d64b";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String jsonString = (String) response.getBody();
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray = jsonObject.getJSONArray("character");
		return CompletableFuture.completedFuture(jsonArray);
	}

	@Async
	public CompletableFuture<JSONArray> getAntiHeroes() {
		logger.info("Get data from Antiheroes");
		String url = "http://www.mocky.io/v2/5ecfd630320000f1aee3d64d";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String jsonString = (String) response.getBody();
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray = jsonObject.getJSONArray("character");
		return CompletableFuture.completedFuture(jsonArray);
	}

	@Async
	public CompletableFuture<JSONArray> getMutants() {
		String url = "http://www.mocky.io/v2/5ecfd6473200009dc1e3d64e";
		logger.info("Get data from Mutants");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		String jsonString = (String) response.getBody();
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray = jsonObject.getJSONArray("character");
		return CompletableFuture.completedFuture(jsonArray);
	}
	
	@Async
	public JSONArray getAvengersArray() throws Exception, Exception 
	{
		logger.info("Get data from Avengers");
		CompletableFuture<JSONArray> avengersArray=getAvengers();
		JSONArray jsonArray=avengersArray.get();
		//System.out.println(jsonArray);
		return jsonArray;	
	}
	
	@Async
	public JSONArray getMutantsArray() throws Exception, Exception 
	{

		CompletableFuture<JSONArray> mutantArray=getMutants();
		JSONArray jsonArray=mutantArray.get();
		//System.out.println(jsonArray);
		return jsonArray;
	}
	
	@Async
	public JSONArray getAntiHeroesArray() throws Exception, Exception 
	{
		//System.out.println("Inside Function");
		CompletableFuture<JSONArray> antiheroArray=getAntiHeroes();
		JSONArray jsonArray=antiheroArray.get();
		//System.out.println(jsonArray);
		return jsonArray;
	}
	
	
	public void getCharacters(JSONArray jsonArray)
	{
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String name = obj.getString("name");
			int max_power = obj.getInt("max_power");
			Characters character = new Characters(name, max_power);
			map.put(name, character);
		}
//		System.out.println(map);
		for (Map.Entry<String, Characters> entry : map.entrySet()) {
			String tempName = entry.getKey();
			Characters characterInfo = entry.getValue();
			int power = characterInfo.getMax_power();
			characterMap.put(tempName, power);
		}
		//return characterMap;
		
	}
	
	public List<Characters> getCharMap() throws Exception
	{
		getCharacters(getAvengersArray());
		getCharacters(getAntiHeroesArray());
		getCharacters(getMutantsArray());
		List<Characters> characters = new ArrayList<>();

		 for(Map.Entry<String, Integer> entry:characterMap.entrySet()) 
		 {
		     Characters character = new Characters(entry.getKey(),entry.getValue());
			 characters.add(character);
		 }

		 return characters;
	}

	}
