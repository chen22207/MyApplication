package com.cs.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cs.myapplication.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshuai12619 on 2015/9/6 13:35.
 */
public class RecyclerViewActivity extends AppCompatActivity {
	private RecyclerView rv;
	private MyRecyclerViewAdapter adapter;
	private List<String> listStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recyclerview);
		initData();
		initView();
		adapter = new MyRecyclerViewAdapter(this, listStr);
		rv.setAdapter(adapter);

		//设置布局管理
		LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rv.setLayoutManager(manager);

		//设置分隔线

	}

	private void initData() {
		listStr = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++) {
			listStr.add((char) i + "");
		}
	}

	private void initView() {
		rv = (RecyclerView) findViewById(R.id.recyclerview1);
	}
}
