package com.cs.mynetworklibrary;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by chenshuai12619 on 2016/3/11 16:30.
 */
public class CsConverterFactory extends Converter.Factory {

	private String xPath;

	public static CsConverterFactory create() {
		return create(new Gson());
	}

	public static CsConverterFactory create(Gson gson) {
		return new CsConverterFactory(gson);
	}

	private final Gson gson;

	private CsConverterFactory(Gson gson) {
		if (gson == null) throw new NullPointerException("gson == null");
		this.gson = gson;
	}

	@Override
	public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
	                                                        Retrofit retrofit) {
		TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//		return new GsonResponseBodyConverter<>(adapter);
		return null;
	}
}
