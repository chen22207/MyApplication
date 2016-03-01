package com.cs.myapplication.customAnimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.cs.myapplication.R;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;

/**
 * Created by chenshuai12619 on 2015/9/21 16:25.
 */
public class CustomAnimationActivity extends AppCompatActivity {
	private Button bt;
	private Animation anim;
	private AnimatorSet mAnimatorSet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_animation);
		bt = (Button) findViewById(R.id.custom_animation_bt);
		anim = AnimationUtils.loadAnimation(this, R.anim.translation_x_100);
		mAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.objectanimation_translation);
		mAnimatorSet.setTarget(bt);
		bt.setOnClickListener((v) -> new SlideDateTimePicker.Builder(getSupportFragmentManager())
				.setListener(new SlideDateTimeListener() {
					@Override
					public void onDateTimeSet(Date date) {

					}
				})
				.setInitialDate(new Date())
				.setIs24HourTime(true)
				.build()
				.show());
	}
}
