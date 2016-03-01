package com.cs.myapplication.ImageLoader;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.myapplication.ImageLoader.bean.FolderBean;
import com.cs.myapplication.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chenshuai12619 on 2015/9/8 14:07.
 */
public class ImageLoaderActivity extends AppCompatActivity {
	private static final int DATA_LOADED = 100;
	private List<String> mImgs;
	private GridView mGridView;
	private TextView mDirNameTv, mDirCountTv;
	private RelativeLayout mBottomRl;
	private ImageAdpater mImageAdpater;
	private File mCurrnetDir;
	private int mMaxCount;
	private List<FolderBean> mFolderBeans = new ArrayList<>();
	private ProgressDialog mProgressDialog;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == DATA_LOADED) {
				mProgressDialog.dismiss();

				data2View();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_loader);
		initView();
		initDatas();
		initEvent();
	}

	private void initView() {
		mGridView = (GridView) findViewById(R.id.image_loader_gv);
		mDirNameTv = (TextView) findViewById(R.id.image_loader_dir_name_tv);
		mDirCountTv = (TextView) findViewById(R.id.image_loader_dir_count_tv);
		mBottomRl = (RelativeLayout) findViewById(R.id.image_loader_bottom_rl);
	}

	private void initDatas() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "存储卡不可使用", Toast.LENGTH_SHORT).show();
			return;
		}
		mProgressDialog = ProgressDialog.show(this, null, "正在加载..");
		new Thread(new Runnable() {
			@Override
			public void run() {
				Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver cr = ImageLoaderActivity.this.getContentResolver();

				Cursor cursor = cr.query(mImgUri, null, MediaStore.Images.Media.MIME_TYPE + "= ? or " + MediaStore.Images.Media.MIME_TYPE + " = ?", new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

				Set<String> mDirPaths = new HashSet<String>();

				while (cursor.moveToNext()) {
					String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					File parentFile = new File(path).getParentFile();
					if (parentFile == null) {
						continue;
					}

					String dirPath = parentFile.getAbsolutePath();
					FolderBean folderBean = null;

					if (mDirPaths.contains(dirPath)) {
						continue;
					} else {
						mDirPaths.add(dirPath);
						folderBean = new FolderBean();
						folderBean.setDir(dirPath);
						folderBean.setFirstImgPath(path);
					}

					if (parentFile.list() == null) {
						continue;
					}

					int picSize = parentFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String filename) {
							return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png");
						}
					}).length;
					folderBean.setCount(picSize);

					mFolderBeans.add(folderBean);

					if (picSize > mMaxCount) {
						mMaxCount = picSize;
						mCurrnetDir = parentFile;
					}

				}

				cursor.close();

				mHandler.sendEmptyMessage(DATA_LOADED);
			}
		}).start();

	}

	private void initEvent() {

	}

	private void data2View() {
		if (mCurrnetDir == null) {
			Toast.makeText(this, "没扫描到图片", Toast.LENGTH_SHORT).show();
			return;
		}

		mImgs = Arrays.asList(mCurrnetDir.list());
		mImageAdpater = new ImageAdpater(this, mImgs, mCurrnetDir.getAbsolutePath());
		mGridView.setAdapter(mImageAdpater);

		mDirCountTv.setText(mMaxCount + "张");
		mDirNameTv.setText(mCurrnetDir.getName());
	}

	private class ImageAdpater extends BaseAdapter {

		private String mDirPath;
		private List<String> mImgPaths;
		private LayoutInflater mInflater;

		public ImageAdpater(Context context, List<String> mDatas, String dirPath) {
			this.mDirPath = dirPath;
			this.mImgPaths = mDatas;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mImgPaths.size();
		}

		@Override
		public Object getItem(int position) {
			return mImgPaths.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_image_loader_gv, parent, false);
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView.findViewById(R.id.item_image_loader_iv);
				holder.ib = (ImageButton) convertView.findViewById(R.id.item_image_loader_selected_ib);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.iv.setImageResource(R.drawable.img_no);
			holder.ib.setImageResource(R.drawable.btn_check_off);

			ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(mDirPath + "/" + mImgPaths.get(position), holder.iv);
			return convertView;
		}

		class ViewHolder {
			ImageView iv;
			ImageButton ib;
		}
	}
}
