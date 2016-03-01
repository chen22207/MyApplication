package com.cs.myapplication.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by chenshuai12619 on 2016/2/15 10:01.
 */
public interface WeatherService {
	@GET("/heweather/weather/free")
	Call<ResponseBody> getWeather(@Query("city") String cityName, @Header("apikey") String apikey);

	@POST
	Call<ResponseBody> getWeather1(@Body String body);
}
