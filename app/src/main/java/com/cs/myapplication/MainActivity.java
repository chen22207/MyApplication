package com.cs.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cs.myapplication.Db.DbActivity;
import com.cs.myapplication.ImageLoader.ImageLoaderActivity;
import com.cs.myapplication.customAnimation.CustomAnimationActivity;
import com.cs.myapplication.customCamera.CustomCamera;
import com.cs.myapplication.databinding.DataBindingActivity;
import com.cs.myapplication.okhttp.OkhttpTestActivity;
import com.cs.myapplication.retrofit.RetrofitTest;
import com.cs.myapplication.rxandroid.ImageGalleryActivity;
import com.cs.myapplication.rxandroid.NormalTest;
import com.cs.myapplication.swipeRefreshLayout.SwipeRefreshLayoutActivity;
import com.cs.myapplication.touchTest.TouchTestActivity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
	private static final Map<Integer, String> mName;
	private static final Map<Integer, Class> mClazz;

	static {
		Map<Integer, String> aMap = new HashMap<>();
		aMap.put(0, "自定义动画");
		aMap.put(1, "图片加载器");
		aMap.put(2, "小圆球下拉刷新和actionbar");
		aMap.put(3, "view事件分发");
		aMap.put(4, "自定义camera");
		aMap.put(5, "DbTest");
		aMap.put(6, "RxAndroid-image-gallery");
		aMap.put(7, "retrofit");
		aMap.put(8, "okhttp");
		aMap.put(9, "rx-normal-test");
        aMap.put(10, "dataBinding");
        mName = Collections.unmodifiableMap(aMap);
	}

	static {
		Map<Integer, Class> classMap = new HashMap<>();
		classMap.put(0, CustomAnimationActivity.class);
		classMap.put(1, ImageLoaderActivity.class);
		classMap.put(2, SwipeRefreshLayoutActivity.class);
		classMap.put(3, TouchTestActivity.class);
		classMap.put(4, CustomCamera.class);
		classMap.put(5, DbActivity.class);
		classMap.put(6, ImageGalleryActivity.class);
		classMap.put(7, RetrofitTest.class);
		classMap.put(8, OkhttpTestActivity.class);
		classMap.put(9, NormalTest.class);
        classMap.put(10, DataBindingActivity.class);
        mClazz = Collections.unmodifiableMap(classMap);
	}

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.main_lv);
		mListView.setAdapter(new MyAdpter(this));
		mListView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(MainActivity.this, mClazz.get(position));
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class MyAdpter extends BaseAdapter {
		private Context context;

		public MyAdpter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return mName.size();
		}

		@Override
		public String getItem(int position) {
			return mName.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(context);
			tv.setText(getItem(position));
			tv.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
			return tv;
		}
	}
}
