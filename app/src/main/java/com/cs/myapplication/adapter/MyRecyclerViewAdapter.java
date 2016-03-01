package com.cs.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs.myapplication.R;

import java.util.List;

/**
 * Created by chenshuai12619 on 2015/9/1.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
	private Context mContext;
	private List<String> mDatas;
	private LayoutInflater mLayoutInflater;

	public MyRecyclerViewAdapter(Context context, List<String> datas) {
		this.mContext = context;
		this.mDatas = datas;
		this.mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = mLayoutInflater.inflate(R.layout.listitem_recyclerview, viewGroup, false);
		MyViewHolder myViewHolder = new MyViewHolder(v);
		return myViewHolder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
		myViewHolder.tv.setText(mDatas.get(i));
	}

	@Override
	public int getItemCount() {
		return this.mDatas.size();
	}
}

class MyViewHolder extends RecyclerView.ViewHolder {
	TextView tv;

	public MyViewHolder(View itemView) {
		super(itemView);
		tv = (TextView) itemView.findViewById(R.id.listitem_recyclerview_tv);
	}
}