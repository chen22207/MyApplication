package com.cs.myapplication.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.cs.myapplication.R;
import com.cs.myapplication.retrofit.module.IpInfo;
import com.cs.myapplication.retrofit.module.Person;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitTest extends AppCompatActivity {

	private EditText ipEt;
	private Button ipBt;
	private Callback<IpInfo> mCallback;

	private final String url = "http://apis.baidu.com";
	private final String apikey = "56852b6c207ec35ce2e4b0e1e8b2e7c1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrofit_test);
		ipEt = (EditText) findViewById(R.id.retrofit_ip_et);
		ipBt = (Button) findViewById(R.id.retrofit_ip_bt);
		ipBt.setOnClickListener((v) -> new Thread(() -> {

			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(url)
					.addConverterFactory(GsonConverterFactory.create())
					.build();

			WeatherService service = retrofit.create(WeatherService.class);
			Call<ResponseBody> call = service.getWeather("hangzhou", apikey);
			try {
				Response<ResponseBody> execute = call.execute();
				String string = execute.body().string();
			} catch (IOException e) {
				e.printStackTrace();
			}

//			IpService ipService = retrofit.create(IpService.class);
//
//			Call<IpInfo> call = ipService.getIpInfo("json", ipEt.getText().toString());
//			call.enqueue(new Callback<IpInfo>() {
//				@Override
//				public void onResponse(Response<IpInfo> response) {
//					System.out.println(response.code());
//				}
//
//				@Override
//				public void onFailure(Throwable t) {
//					System.out.println(t.getMessage());
//				}
//			});

		}).start());

//		Gson gson = new Gson();
//		String jsonStr = "{'name1':'tom','person':{'name':'jack','age':'23'}}";
//		Person p = gson.fromJson(jsonStr, Person.class);
//		System.out.println(p.toString());

		JsonObject json = new JsonObject();
		json.addProperty("name", "tom");
		String jj = json.toString();
		JsonParser parser = new JsonParser();
		JsonElement parse = parser.parse(jj);
		Gson gson = new Gson();
		Person p = gson.fromJson(jj, Person.class);
		Person pp = new Person();
		Person person = gson.fromJson(parse, Person.class);
		System.out.println(jj);


	}

	private class mConverterFackory extends Converter.Factory {
		@Override
		public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
			return super.responseBodyConverter(type, annotations, retrofit);
		}
	}


}
