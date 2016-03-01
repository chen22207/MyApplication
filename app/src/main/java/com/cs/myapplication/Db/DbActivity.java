package com.cs.myapplication.Db;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cs.myapplication.R;

import java.io.File;

public class DbActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db);
		String path1 = Environment.getDataDirectory().getAbsolutePath();
		String path = Environment.getExternalStorageDirectory().getAbsolutePath();
		String path2 = Environment.getRootDirectory().getAbsolutePath();
		String path3 = this.getPackageName();
		String path4 = path1 + "/data/" + path3 + "/daYT";
		String fileName = "abc.txt";
		File file = new File(path4);
		if (!file.exists()) {
			boolean b = false;
			try {
				b = file.mkdirs();
				File f = new File(file, fileName);
				if (!f.exists()) {
					f.createNewFile();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d("db", b ? "a" : "b");
		}
		Log.d("db", path);
		Log.d("db", path1);
		Log.d("db", path2);
	}
}
