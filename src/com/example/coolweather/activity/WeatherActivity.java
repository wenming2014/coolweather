package com.example.coolweather.activity;

import com.example.coolweather.R;
import com.example.coolweather.util.HttpCallbackListener;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.youmi.android.AdView;

public class WeatherActivity extends Activity implements OnClickListener{
	private LinearLayout weatherInfoLayout;
	private TextView cityName;
	private TextView publishText;
	private TextView temp1Text;
	private TextView temp2Text;
	private TextView weatherDesp;
	private TextView currentDate;
	private Button switchCity;
	private Button refreshWeather;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		AdView adView=new AdView(this);	
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adViewLayout);
		adLayout.addView(adView,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		weatherInfoLayout=(LinearLayout)findViewById(R.id.weather_info_layout);
		cityName=(TextView)findViewById(R.id.city_name);
		publishText=(TextView)findViewById(R.id.publish_text);
		temp1Text=(TextView)findViewById(R.id.temp1);
		temp2Text=(TextView)findViewById(R.id.temp2);
		weatherDesp=(TextView)findViewById(R.id.weather_desp);
		currentDate=(TextView)findViewById(R.id.current_date);
		switchCity=(Button)findViewById(R.id.switch_city);
		refreshWeather=(Button)findViewById(R.id.refresh_weather);
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);
		
		String countyCode=getIntent().getStringExtra("county_code");
		if(!TextUtils.isEmpty(countyCode)){
			publishText.setText("同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}else
			showWeather();
		}
	private void queryWeatherCode(String countyCode){
		String address="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address,"countyCode");
	}
	private void queryWeatherInfo(String weatherCode){
		String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address,"weatherCode");
	}
	private void queryFromServer(final String address,final String type){
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener(){

			@Override
			public void onFinish(final String response) {
				// TODO Auto-generated method stub
				if("countyCode".equals(type)){
					if(!TextUtils.isEmpty(response)){
						String array[]=response.split("\\|");
						if(array!=null && array.length==2){
							String weatherCode=array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}else if("weatherCode".equals(type)){
					Utility.handleWeatherResponse(WeatherActivity.this, response);
					runOnUiThread(new Runnable(){
						public void run(){
							showWeather();
						}
					});
				}
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable(){
					public void run(){
						publishText.setText("同步失败");
					}
				});
			}
			
		});
	}
	
	private void showWeather(){
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		cityName.setText(prefs.getString("city_name",""));
		temp1Text.setText(prefs.getString("temp1",""));
		temp2Text.setText(prefs.getString("temp2",""));
		weatherDesp.setText(prefs.getString("weather_desp",""));
		publishText.setText("今天"+prefs.getString("publish_time", "")+"发布");
		currentDate.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityName.setVisibility(View.VISIBLE);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.switch_city:
			Intent intent=new Intent(this,ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("同步中");
			SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode=prefs.getString("weather_code", "");
			if(!TextUtils.isEmpty(weatherCode))
				queryWeatherInfo(weatherCode);
			break;
	    default:
	    	break;
		}
	}

}
