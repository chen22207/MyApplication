package com.cs.mynetworklibrary;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by chenshuai12619 on 2016/3/11 17:17.
 */
final class CsGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private String xPath;
    private final TypeAdapter<T> adapter;

    CsGsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this(adapter, null);
    }

    CsGsonResponseBodyConverter(TypeAdapter<T> adapter, String xPath) {
        this.adapter = adapter;
        this.xPath = xPath;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        if (TextUtils.isEmpty(xPath)) {
            try {
                return adapter.fromJson(value.charStream());
            } finally {
                value.close();
            }
        } else {
            String[] paths = xPath.split("/");
            try {
                JSONObject root = new JSONObject(value.string());
                for (String path : paths) {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
