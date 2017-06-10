package com.example.coolweather.db;

public class City {
	private int id;
	private String cityName;
	private String cityCode;
	private int provinceId;
	
	public int getId(){
		return id;
	}
	
	public int getProvinceId(){
		return provinceId;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	public String getCityCode(){
		return cityCode;
	}
		
	public void setId(int id){
		this.id=id;
	}
	public void setProvinceId(int id){
		this.provinceId=id;
	}
	
	
	public void setCityName(String CityName){
		this.cityName=CityName;
	}
	
	public void setCityCode(String CityCode){
		this.cityCode=CityCode;
	}
	


}
