package com.acadplnr;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class MainActivity extends TabActivity{
	Global1  globe;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
		 
		TabHost tabHost = getTabHost(); 
 
		// Home tab
		Intent intent1 = new Intent().setClass(this, Home.class);
		TabSpec tabSpec1 = tabHost
		  .newTabSpec("home")
		  .setIndicator("HOME")
		  .setContent(intent1);
 
		// TimeTable tab
		Intent intent2 = new Intent().setClass(this, TimeTable.class);
		TabSpec tabSpec2 = tabHost
		  .newTabSpec("timetable")
		  .setIndicator("Time Table")
		  .setContent(intent2);
 
		// Calendar tab
		Intent intent3 = new Intent().setClass(this, Calendar.class);
		TabSpec tabSpec3 = tabHost
		  .newTabSpec("exscheduler")
		  .setIndicator("Exam Scheduler")
		  .setContent(intent3);
 
		// Notification tab
		Intent intent4 = new Intent().setClass(this, Notification.class);
		TabSpec tabSpec4 = tabHost
		  .newTabSpec("notes")
		  .setIndicator("My Notes")
		  .setContent(intent4);
 
		// add all tabs 
		tabHost.addTab(tabSpec1);
		tabHost.addTab(tabSpec2);
		tabHost.addTab(tabSpec3);
		tabHost.addTab(tabSpec4);
 
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (globe.done == 1)
		{
			finish();
		}
	}
	
	
 
}