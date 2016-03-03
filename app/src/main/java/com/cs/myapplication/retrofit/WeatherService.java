package com.cs.myapplication.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenshuai12619 on 2016/2/15 10:01.
 */
public interface WeatherService {
	@GET("/heweather/weather/free")
	Call<ResponseBody> getWeather(@Query("city") String cityName, @Header("apikey") String apikey);

	@Headers("apikey:56852b6c207ec35ce2e4b0e1e8b2e7c1")
	@GET("/heweather/weather/free")
	Observable<ResponseBody> getWeather1(@Query("city") String cityName);
	
}
