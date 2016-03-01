package com.cs.myapplication.retrofit.converter;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by chenshuai12619 on 2016/2/3 11:25.
 */
public final class MyGsonConverterFactory extends Converter.Factory {
	public static MyGsonConverterFactory create() {
		return create(new Gson());
	}

	public static MyGsonConverterFactory create(Gson gson) {
		return new MyGsonConverterFactory(gson);
	}

	private final Gson gson;

	private MyGsonConverterFactory(Gson gson) {
		if (gson == null) throw new NullPointerException("gson == null");
		this.gson = gson;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
		return super.responseBodyConverter(type, annotations, retrofit);
	}
}
