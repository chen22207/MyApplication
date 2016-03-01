package com.cs.myapplication.touchTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.myapplication.R;

import static com.cs.myapplication.R.id.touch_test_button;

public class TouchTestActivity extends AppCompatActivity {
	private RelativeLayout root;
	private MyCustomlinearLayout parent;
	private Button button;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch_test);
		root = (RelativeLayout) findViewById(R.id.touch_test_root_rl);
		parent = (MyCustomlinearLayout) findViewById(R.id.touch_test_parent);
		button = (Button) findViewById(touch_test_button);
		textView = (TextView) findViewById(R.id.touch_test_text);
		button.setOnTouchListener((v, event) -> {
			Log.d("button", "click");
			return false;
		});
		textView.setOnTouchListener((v, event) -> {
			Log.d("textview", "click");
			return false;
		});
	}
}
