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
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
//		test1();
		RxView.clicks(ipBt)
				.throttleFirst(1000, TimeUnit.MILLISECONDS)
				.subscribe(aVoid -> {
					System.out.println("click");
				});

		RxTextView.textChanges(ipEt)
				.subscribe(System.out::println);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(url)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		WeatherService service = retrofit.create(WeatherService.class);
		service.getWeather1("hangzhou")
				.flatMap(responseBody -> service.getWeather1("beijing"))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<ResponseBody>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						System.out.println(e.getMessage());
					}

					@Override
					public void onNext(ResponseBody responseBody) {
						try {
							String s = responseBody.string();
							System.out.println(s);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});


	}

	private void print(CharSequence charSequence) {
		System.out.println(charSequence);
	}

	private void test1() {
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


}
