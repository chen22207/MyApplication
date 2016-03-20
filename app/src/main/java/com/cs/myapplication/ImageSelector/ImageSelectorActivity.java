package com.cs.myapplication.ImageSelector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.cs.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class ImageSelectorActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE = 1;
    Button bt;
    MyDisableScrollGridview gv;

    ArrayList<String> defaultDataArray = new ArrayList<>();
    int mGridWidth;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);
        initView();
        initEvent();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            width = size.x;
        } else {
            width = wm.getDefaultDisplay().getWidth();
        }
        mGridWidth = width / 5;
    }

    private void initEvent() {
        bt.setOnClickListener(v -> {
            Intent intent = new Intent(this, MultiImageSelectorActivity.class);
// whether show camera
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
            intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
            intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
            startActivityForResult(intent, REQUEST_IMAGE);
        });
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

    }

    private void initView() {
        bt = (Button) findViewById(R.id.image_selector_bt);
        gv = (MyDisableScrollGridview) findViewById(R.id.image_selector_gv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // Get the result list of select image paths
                defaultDataArray = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // do your logic ....
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return defaultDataArray.size() + 1;
        }

        @Override
        public String getItem(int position) {
            return defaultDataArray.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == getCount() - 1) {
                ImageView iv = new ImageView(ImageSelectorActivity.this);
                iv.setImageResource(R.drawable.ic_action_search);
                return iv;
            } else {
                ImageView iv2 = new ImageView(ImageSelectorActivity.this);
                Picasso.with(ImageSelectorActivity.this)
                        .load(new File(getItem(position)))
                        .placeholder(me.nereo.multi_image_selector.R.drawable.default_error)
                        .resize(mGridWidth, mGridWidth)
                        .centerCrop()
                        .into(iv2);
                return iv2;
            }
        }
    }

}
