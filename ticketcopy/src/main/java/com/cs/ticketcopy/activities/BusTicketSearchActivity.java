package com.cs.ticketcopy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cs.ticketcopy.R;

public class BusTicketSearchActivity extends AppCompatActivity {
	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_ticket_search);
		initView();

	}

	private void initView() {
		mRecyclerView = (RecyclerView) findViewById(R.id.bus_ticket_search_rv);

	}

	private class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return null;
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {

		}

		@Override
		public int getItemCount() {
			return 0;
		}
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		public ViewHolder(View itemView) {
			super(itemView);
		}
	}
}
