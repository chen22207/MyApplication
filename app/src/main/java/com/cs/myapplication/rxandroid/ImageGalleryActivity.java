package com.cs.myapplication.rxandroid;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.cs.myapplication.R;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by chenshuai12619 on 2016/1/15 14:59.
 */
public class ImageGalleryActivity extends AppCompatActivity {
	private GridView mGridView;
	private ArrayList<File> files = new ArrayList<>();
	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_gallery);
		mGridView = (GridView) findViewById(R.id.iamge_gallery_gv);
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM";
		File file = new File(path);
		if (file.exists()) {
			getFiles(file);
		}
		mAdapter = new MyAdapter(this);
		mGridView.setAdapter(mAdapter);
	}

	private void getFiles(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file1 : files) {
				getFiles(file1);
			}
		} else if (file.isFile()) {
			if (file.getName().toLowerCase().endsWith(".jpg")) {
				files.add(file);
			}
		}
	}

	private class MyAdapter extends BaseAdapter {
		private Context mContext;

		public MyAdapter(Context context) {
			this.mContext = context;
		}

		@Override
		public int getCount() {
			return files.size();
		}

		@Override
		public File getItem(int position) {
			return files.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv;
			if (convertView == null) {
				iv = new ImageView(mContext);
				File f = getItem(position);
				iv.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
				iv.setScaleType(ImageView.ScaleType.FIT_XY);
			} else {
				iv = (ImageView) convertView;
			}
			return iv;
		}
	}
}
