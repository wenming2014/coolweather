package com.example.coolweather.db;

public class Province {
	private int id;
	private String provinceName;
	private String provinceCode;
	
	public int getId(){
		return id;
	}
	
	public String getProvinceName(){
		return provinceName;
	}
	
	public String getProvinceCode(){
		return provinceCode;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
	public void setProvinceName(String ProvinceName){
		this.provinceName=ProvinceName;
	}
	
	public void setProvinceCode(String ProvinceCode){
		this.provinceCode=ProvinceCode;
	}


}
