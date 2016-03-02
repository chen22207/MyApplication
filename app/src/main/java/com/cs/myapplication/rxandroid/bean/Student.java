package com.cs.myapplication.rxandroid.bean;

import java.util.List;

/**
 * Created by chenshuai12619 on 2016/3/2 15:48.
 */
public class Student {
	private String name;
	private List<Course> courses;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
