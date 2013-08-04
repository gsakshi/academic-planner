package com.acadplnr;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Home extends Activity{
	Button addCourse;
	Global1 globe;
	// Declare the UI components
	public ListView courseListView;
	// Declare an array to store data to fill the list

	int NO_COURSE_ADDED=0;
	public ArrayAdapter arrayAdapter;
	int m;
	
	ArrayList<String> al = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		final Animation animation = new AlphaAnimation(1, 0); 
		animation.setDuration(500);
		animation.setInterpolator(new LinearInterpolator());
		 animation.setRepeatCount(Animation.INFINITE);
		 animation.setRepeatMode(Animation.REVERSE);
		 
		// Initialize the UI components
       courseListView = (ListView) findViewById(R.id.listView);
        
     // Initialize the songs array
  

       
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        
        ///////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
      Crs_database_help dinfo = new Crs_database_help(Home.this);
      
   dinfo.open();
	
		String[] data;
		String alldata ;
		
		NO_COURSE_ADDED=0;	
			//String course1 = dinfo.GetCourseNameById(1);
			//al.add(course1);
			if(dinfo.GetAllCourses()== null)
			{
				al.add("noCourseAddedYet" );
				NO_COURSE_ADDED=1;
			}
			else
			{
				alldata = dinfo.GetAllCourses(); 

				data = alldata.split("---");
			//splits data into an array of strings containing course_names
				for(int j=0;j<data.length;j++)
				{
					al.add(data[j]);
				}
				
			}
		
		dinfo.close();
		
		
		
		
		///////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
		
		courseListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				/////
				/////
				
				
				try{
					if(NO_COURSE_ADDED==1)
					{
						Intent i = new Intent("com.acadplnr.noCourse");
						startActivity(i);
						
					}
					else{
						
					
					Class Myclass = Class.forName("com.acadplnr.CourseDisplay");
					Intent myintent = new Intent(Home.this,Myclass);
					Bundle basket = new Bundle();
					position++;
					String Position = "" + position;
					basket.putString("position", Position);
					myintent.putExtras(basket);
					startActivity(myintent);
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					String error = e.toString();
					Dialog h = new Dialog(Home.this);
					h.setTitle(" :(");
					TextView tv1 = new TextView(Home.this);
					tv1.setText(error);
					h.setContentView(tv1);
					h.show();
				}
				////
				////
			}
		});
		
		////////////////////////////////////////////////
		////////////////////////////////////
		///////////////////
        
        
        
        
        courseListView.setAdapter(arrayAdapter);
        registerForContextMenu(courseListView);
        addCourse = (Button) findViewById(R.id.addCourse);
        addCourse.startAnimation(animation);
        addCourse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				v.clearAnimation();
				
				addCourse.startAnimation(AnimationUtils.loadAnimation(Home.this, R.anim.translate));
				
				
				Intent i = new Intent("com.acadplnr.CourseName");
				startActivity(i);
			}
		});
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Select The Action"); 
        menu.add(0, v.getId(), 0, "Edit"); 
        menu.add(0, v.getId(), 0, "Delete");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		m =info.position +1;
        if(item.getTitle()=="Edit")
        {

           // Code to execute when clicked on This Item
        	Bundle v = new Bundle();
        	String value = ""+ m ;
        	v.putString("clkpos", value);
        
        Intent w = new Intent("com.acadplnr.Update");
        	w.putExtras(v);
   		startActivity(w);
         } 
        else if(item.getTitle()=="Delete")
        {
        	AlertDialog.Builder adb = new AlertDialog.Builder(
        			Home.this);
        	        adb.setTitle("Delete Course");
        			adb.setMessage("Are u sure to delete " 
        			+ al.get(info.position) + " ?" );
        			adb.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Crs_database_help info = new Crs_database_help(Home.this);
							info.open();
							info.deleteEntry(m);
							
							info.close();
							finish();
							Intent i = new Intent(Home.this,MainActivity.class);
							startActivity(i);
						}
					});
        			adb.show();  
          // Code to execute when clicked on This Item   
        }  
        else
        {return false;} 
        return true; 
        }
	

		
}
	
       

