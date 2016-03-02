package com.cs.myapplication.rxandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cs.myapplication.R;
import com.cs.myapplication.rxandroid.bean.Course;
import com.cs.myapplication.rxandroid.bean.Student;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class NormalTest extends AppCompatActivity {
	private static final String TAG = "NormalTest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_normal_test);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});


		Student chen = new Student();
		chen.setName("chen");
		List<Course> courses = new ArrayList<>();
		courses.add(new Course("Math", "jack"));
		courses.add(new Course("History", "tom"));
		chen.setCourses(courses);

		Student li = new Student();
		li.setName("li");
		List<Course> courses1 = new ArrayList<>();
		courses1.add(new Course("English", "lisa"));
		courses1.add(new Course("Physics", "John"));
		courses1.add(new Course("Chemistry", "stephen"));
		li.setCourses(courses1);

		Student[] students = {chen, li};
		Subscriber<Course> subscriber = new Subscriber<Course>() {
			@Override
			public void onCompleted() {
				System.out.println("complete");
			}

			@Override
			public void onError(Throwable e) {
				System.out.println(e.getMessage());
			}

			@Override
			public void onNext(Course course) {
				Log.d(TAG, course.getCourseName());
			}
		};
		Observable.from(students)
				.flatMap(student -> Observable.from(student.getCourses()))
				.subscribe(subscriber);

	}

}
