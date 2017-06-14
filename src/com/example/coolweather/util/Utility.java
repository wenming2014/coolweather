package com.example.coolweather.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import com.example.coolweather.db.City;
import com.example.coolweather.db.CoolWeatherDB;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
	
	public static void handleWeatherResponse(Context context,String response){
		try {
			JSONObject jsonObject=new JSONObject(response);
			JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
			String cityName=weatherInfo.getString("city");
			String weatherCode=weatherInfo.getString("cityid");
			String temp1=weatherInfo.getString("temp1");
			String temp2=weatherInfo.getString("temp2");
			String weatherDesp=weatherInfo.getString("weather");
			String publishTime=weatherInfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void saveWeatherInfo(Context context,String cityName,String weatherCode,String temp1,String temp2,String weatherDesp,String publishTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy��M��d��",Locale.CHINA);
		SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected",true);
		editor.putString("city_name",cityName);
		editor.putString("weather_code",weatherCode);
		editor.putString("temp1",temp1);
		editor.putString("temp2",temp2);
		editor.putString("weather_desp",weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
		
		
	}
	

}
