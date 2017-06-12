package com.example.coolweather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	//定义数据库名
	public static final String DB_NAME="cool_weather";
	
	public static final int VERSION=3;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper helper=new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db=helper.getWritableDatabase();
	}
	
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB==null)
			coolWeatherDB=new CoolWeatherDB(context);
		return coolWeatherDB;
	}
	
	public void saveProvince(Province Province){
		if(Province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", Province.getProvinceName());
			values.put("province_code", Province.getProvinceCode());
			db.insert("province", null, values);
		}
	}
	
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province Province=new Province();
				Province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				Province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				Province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(Province);
			}while(cursor.moveToNext());
			if(cursor!=null)
				cursor.close();
			}
		return list;
	}
		
	public void saveCity(City City){
		if(City!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", City.getCityName());
			values.put("city_code", City.getCityCode());
			values.put("province_id",City.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	public List<City> loadCitys(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City City=new City();
				City.setId(cursor.getInt(cursor.getColumnIndex("id")));
				City.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				City.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				City.setProvinceId(provinceId);
				list.add(City);
			}while(cursor.moveToNext());
			if(cursor!=null)
				cursor.close();
			}
		return list;
	}
	
	public void saveCounty(County County){
		if(County!=null){
			ContentValues values=new ContentValues();
			values.put("county_name", County.getCountyName());
			values.put("county_code", County.getCountyCode());
			values.put("city_id", County.getCityId());
			db.insert("County", null, values);
		}
	}
	
	public List<County> loadCountys(int cityId){
		List<County> list=new ArrayList<County>();
		Cursor cursor=db.query("County", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				County County=new County();
				County.setId(cursor.getInt(cursor.getColumnIndex("id")));
				County.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				County.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				County.setCityId(cityId);
				list.add(County);
			}while(cursor.moveToNext());
			if(cursor!=null)
				cursor.close();
			}
		return list;
	}
	
	

}
