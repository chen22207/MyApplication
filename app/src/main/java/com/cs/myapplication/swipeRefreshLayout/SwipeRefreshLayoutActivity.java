package com.cs.myapplication.swipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs.myapplication.R;

import java.util.ArrayList;

/**
 * Created by chenshuai12619 on 2015/9/22 16:39.
 */
public class SwipeRefreshLayoutActivity extends AppCompatActivity {
	private SwipeRefreshLayout srl;
	private ListView lv;
	private ArrayList<String> lists = new ArrayList<>();
	private ArrayAdapter<String> mAdapter;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mAdapter.notifyDataSetChanged();
			srl.setRefreshing(false);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swiperefreshlayout);
		srl = (SwipeRefreshLayout) findViewById(R.id.swiperl_srl);
		lv = (ListView) findViewById(R.id.swiprl_lv);
		for (int i = 0; i < 10; i++) {
			lists.add("数据" + i);
		}
		mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lists);
		lv.setAdapter(mAdapter);
		srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				requestNetData();
			}

		});
		srl.setColorSchemeResources(android.R.color.holo_blue_light);
		//srl.setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.holo_blue_light));
	}

	private void requestNetData() {
		for (int i = 0; i < 5; i++) {
			lists.add("额外" + i);
		}
		mHandler.sendEmptyMessageDelayed(100, 2000);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_swipeactivity, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
