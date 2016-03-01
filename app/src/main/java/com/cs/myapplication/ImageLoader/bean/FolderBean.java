package com.cs.myapplication.ImageLoader.bean;

/**
 * Created by chenshuai12619 on 2015/9/21 9:41.
 */
public class FolderBean {
	private String dir;
	private String firstImgPath;
	private String name;
	private int count;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;

		int lastIndexOf = this.dir.indexOf("/");
		this.name = this.dir.substring(lastIndexOf);
	}

	public String getFirstImgPath() {
		return firstImgPath;
	}

	public void setFirstImgPath(String firstImgPath) {
		this.firstImgPath = firstImgPath;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
