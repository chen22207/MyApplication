package com.cs.myapplication.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cs.myapplication.R;
import com.cs.myapplication.databinding.bean.User;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.cs.myapplication.databinding.ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        User user = new User("tom", 12, "abc street");
        binding.setUser(user);


    }

}
