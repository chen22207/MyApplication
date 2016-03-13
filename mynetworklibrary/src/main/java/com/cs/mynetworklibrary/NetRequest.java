package com.cs.mynetworklibrary;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by chenshuai12619 on 2016/2/16 11:07.
 */
public class NetRequest<T> {
    public T sendRequest(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(CsConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

}
