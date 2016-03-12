package com.cs.ticketcopy;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cs.ticketcopy.fragments.MainTwoFragment;

public class MainActivity extends AppCompatActivity {
	private Toolbar mToolbar;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavigationView;
	private TextView userNameTv;
	private ActionBarDrawerToggle mDrawerToggle;
	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	private MyPagerAdapter mAdapter;
	private Fragment[] mFragments = {new MainTwoFragment(), new MainTwoFragment(), new MainTwoFragment()};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initTab();
		initEvent();
		userNameTv.setText("heh");
	}

	private void initView() {
		mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
		mToolbar.setNavigationIcon(R.drawable.icon_defult_header);
		setSupportActionBar(mToolbar);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,
				mToolbar,
				R.string.tip_navigation_open,
				R.string.tip_navigation_close
		);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

		mTabLayout = (TabLayout) findViewById(R.id.main_tablayout);
		mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
		userNameTv = (TextView) findViewById(R.id.navigation_header_name_tv);
	}

	private void initTab() {

	}

	private void initEvent() {
		mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments);
		mViewPager.setAdapter(mAdapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {
		private int[] mTitle = {R.string.main_tab_local, R.string.main_tab_bus_number_search, R.string.main_tab_my_ticket};
		private Fragment[] fs;

		public MyPagerAdapter(FragmentManager fm, Fragment[] fragments) {
			super(fm);
			this.fs = fragments;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getString(mTitle[position]);
		}

		@Override
		public int getCount() {
			return fs.length;
		}

		@Override
		public Fragment getItem(int position) {
			return fs[position];
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
