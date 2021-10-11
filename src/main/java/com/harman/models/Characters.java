package com.harman.models;

public class Characters implements Comparable<Characters>{
	
	private String name;
	private Integer max_power;
	private Integer accessCount = 0;
	
	
	public Characters(String name, Integer max_power)
	{
		this.name=name;
		this.max_power=max_power;
	}
	
	
	public Characters(String name) {
		super();
		this.name = name;
	}

	public void setMax_power(Integer max_power) {
		this.max_power = max_power;
	}

	public Integer getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}

	public Characters() {
		super();
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMax_power() {
		return max_power;
	}


	@Override
	public String toString() {
		return "Characters [name=" + name + ", max_power=" + max_power +  " ,access count=" + accessCount+ "]" ;
	}


	@Override
	public int compareTo(Characters o) {
		return o.getMax_power().compareTo(this.getMax_power());
	}
}
