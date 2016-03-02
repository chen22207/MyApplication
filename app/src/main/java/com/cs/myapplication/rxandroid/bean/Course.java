package com.cs.myapplication.rxandroid.bean;

/**
 * Created by chenshuai12619 on 2016/3/2 15:48.
 */
public class Course {
	private String courseName;
	private String teacher;

	public Course(String courseName, String teacher) {
		this.courseName = courseName;
		this.teacher = teacher;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
}
