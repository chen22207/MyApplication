package com.cs.myapplication.customCamera;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import com.cs.myapplication.R;

import java.io.IOException;

/**
 * Created by chenshuai12619 on 2015/11/25 15:13.
 */
public class CustomCamera extends AppCompatActivity implements SurfaceHolder.Callback {
	private Button bt;
	private SurfaceView sv;
	private SurfaceHolder mSurfaceHolder;
	private Camera mCamera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_camera);
		bt = (Button) findViewById(R.id.custom_camera_takepic_bt);
		sv = (SurfaceView) findViewById(R.id.custom_camera_sv);
		mSurfaceHolder = sv.getHolder();
		mSurfaceHolder.addCallback(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mCamera == null) {
			mCamera = getCamera();
		}
		startPreview(mCamera, mSurfaceHolder);
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera();
	}

	private Camera getCamera() {
		Camera camera;
		try {
			camera = Camera.open();
		} catch (Exception e) {
			camera = null;
			e.printStackTrace();
		}
		return camera;
	}

	private void startPreview(Camera camera, SurfaceHolder holder) {
		if (mCamera != null && mSurfaceHolder != null) {
			try {
				camera.setPreviewDisplay(holder);
				camera.setDisplayOrientation(90);
				camera.startPreview();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "相机服务不可用", Toast.LENGTH_SHORT).show();
		}

	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		startPreview(mCamera, mSurfaceHolder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if (mCamera != null) {
			mCamera.stopPreview();
		}
		startPreview(mCamera, mSurfaceHolder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		releaseCamera();
	}
}
