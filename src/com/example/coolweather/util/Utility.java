package com.example.coolweather.util;

import com.example.coolweather.db.City;
import com.example.coolweather.db.CoolWeatherDB;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;

public class Utility {
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,String response){
		String[] allProvinces=response.split(",");
		if(allProvinces!=null && allProvinces.length>0){
			for(String p:allProvinces){
				Province province=new Province();
				String[] array=p.split("\\|");
				province.setProvinceCode(array[0]);
				province.setProvinceName(array[1]);
				coolWeatherDB.saveProvince(province);
			}
			return true;
		}
		return false;
	}
	
	public synchronized static boolean handleCitysResponse(CoolWeatherDB coolWeatherDB,String response,int provinceId){
		String[] allCitys=response.split(",");
		if(allCitys!=null && allCitys.length>0){
			for(String p:allCitys){
				City City=new City();
				String[] array=p.split("\\|");
				City.setCityCode(array[0]);
				City.setCityName(array[1]);
				City.setProvinceId(provinceId);
				coolWeatherDB.saveCity(City);
			}
			return true;
		}
		return false;
	}
	
	public synchronized static boolean handleCountysResponse(CoolWeatherDB coolWeatherDB,String response,int cityId){
		String[] allCountys=response.split(",");
		if(allCountys!=null && allCountys.length>0){
			for(String p:allCountys){
				County County=new County();
				String[] array=p.split("\\|");
				County.setCountyCode(array[0]);
				County.setCountyName(array[1]);
				County.setCityId(cityId);
				coolWeatherDB.saveCounty(County);
			}
			return true;
		}
		return false;
	}

}
