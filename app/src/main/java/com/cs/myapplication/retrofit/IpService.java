package com.cs.myapplication.retrofit;

import com.cs.myapplication.retrofit.module.IpInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenshuai12619 on 2016/1/20 15:00.
 */
public interface IpService {
	@GET("iplookup/iplookup.php")
	Call<IpInfo> getIpInfo(@Query("format") String format, @Query("ip") String ip);
}
