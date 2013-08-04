package com.acadplnr;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
 
		Intent intent3 = new Intent().setClass(this, Examscheduler.class);
		TabSpec tabSpec3 = tabHost
		  .newTabSpec("exam")
		  .setIndicator("Exam Schedule")
		  .setContent(intent3);
 
		// Notification tab
		Intent intent4 = new Intent().setClass(this, Notes.class);
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

		if (Global1.tabN == 1)
		{
			
			tabHost.setCurrentTab(3);
		}
		else{
		tabHost.setCurrentTab(0);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 MenuInflater inflater = getMenuInflater();
         inflater.inflate(R.menu.menu, menu);
         return true;
	}
    


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// Handle item selection
        
        
        switch (item.getItemId()) {
        case R.id.exit:
        	finish();
        	return true;
        case R.id.aboutApp:
        	Intent i = new Intent("com.acadplnr.aboutApp");
        	startActivity(i);
        	return true;
        
        default:
            return super.onOptionsItemSelected(item); 	
        }
		
	}

	
 
}