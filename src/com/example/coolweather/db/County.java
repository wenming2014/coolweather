package com.example.coolweather.db;

public class County {
	private int id;
	private String countyName;
	private String countyCode;
	private int cityId;
	
	public int getId(){
		return id;
	}
	
	public int getCityId(){
		return cityId;
	}
	
	public String getCountyName(){
		return countyName;
	}
	
	public String getCountyCode(){
		return countyCode;
	}
		
	public void setId(int id){
		this.id=id;
	}
	public void setCityId(int id){
		this.cityId=id;
	}
		
	public void setCountyName(String countyName){
		this.countyName=countyName;
	}
	
	public void setCountyCode(String countyCode){
		this.countyCode=countyCode;
	}
	


}
