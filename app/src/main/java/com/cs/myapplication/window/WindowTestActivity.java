package com.cs.myapplication.window;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.cs.myapplication.R;

/**
 * Created by chenshuai12619 on 2015/11/30 10:31.
 */
public class WindowTestActivity extends AppCompatActivity implements View.OnClickListener {
	private Button bt;
	private Window mWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_test);
		bt = (Button) findViewById(R.id.window_test_bt);
		bt.setOnClickListener(this);
		initWindow();
	}

	private void initWindow() {

	}

	@Override
	public void onClick(View v) {

	}
}
