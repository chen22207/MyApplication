package com.cs.myapplication.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cs.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpTestActivity extends AppCompatActivity {

	private Button bt;
	private OkHttpClient client;
	private final String url = "http://apis.baidu.com/heweather/weather/fee";
	private final String apikey = "56852b6c207ec35ce2e4b0e1e8b2e7c1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_okhttp_test);
		bt = (Button) findViewById(R.id.okhttp_test_bt);
		bt.setOnClickListener(mOnClickListener);
		client = new OkHttpClient();
	}

	public View.OnClickListener mOnClickListener = v -> {
		request();
	};

	public void request() {
		HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
				.addQueryParameter("city", "beijing")
				.build();
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "");
		Request request = new Request.Builder()
				.addHeader("apikey", apikey)
				.post(body)
				.url(httpUrl)
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				System.out.println("a");
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String body = response.body().string();
				try {
					JSONObject jsonObject = new JSONObject(body);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

	}
}
