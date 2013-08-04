package com.acadplnr;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Notes extends Activity implements OnClickListener{
	
	Global1 globe;
	Button AddNotes;
	ListView lv1;
	public ArrayAdapter arrayAdapter;
	ArrayList<String> nl = new ArrayList<String>();
	int NO_COURSE_ADDED=0;
	int p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes);
		
		Animation vanish =AnimationUtils.loadAnimation(this,R.anim.vanish);
		
		
		AddNotes = (Button) findViewById(R.id.addNotes);
		AddNotes.setOnClickListener(this);
		
		findViewById(R.id.addNotes).startAnimation(vanish);
		
		lv1 = (ListView) findViewById(R.id.listView1);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nl);
		
        Notes_database dinfo = new Notes_database(Notes.this);
        
        dinfo.open();
        
        String[] ndata;
		String nalldata ;
		
		NO_COURSE_ADDED=0;	
		//String course1 = dinfo.GetCourseNameById(1);
		//al.add(course1);
		if(dinfo.GetAllNotes()== null)
		{
			nl.add("noNotesAddedYet" );
			NO_COURSE_ADDED=1;
		}
		else
		{
			nalldata = dinfo.GetAllNotes(); 

			ndata = nalldata.split("-");
		//splits data into an array of strings containing course_names
			for(int j=0;j<ndata.length;j++)
			{
				nl.add(ndata[j]);
			}
			
		}
	
	dinfo.close();
	
		
	lv1.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			/////
			/////
			
			
			try{
				if(NO_COURSE_ADDED==1)
				{
					Intent i = new Intent("com.acadplnr.noNotes");
					startActivity(i);
					
				}
				else{
					
				
				Class Myclass = Class.forName("com.acadplnr.NotesDisplay");
				Intent myintent = new Intent(Notes.this,Myclass);
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
				Dialog h = new Dialog(Notes.this);
				h.setTitle(" :(");
				TextView tv1 = new TextView(Notes.this);
				tv1.setText(error);
				h.setContentView(tv1);
				h.show();
			}
			////
			////
		}
	});
	
	
	
        lv1.setAdapter(arrayAdapter);
        registerForContextMenu(lv1);
		
        
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		AddNotes.startAnimation(AnimationUtils.loadAnimation(Notes.this, R.anim.anim_rotate));
		
		Intent i = new Intent("com.acadplnr.EditNotes");
		startActivity(i);
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
		p =info.position +1;
        if(item.getTitle()=="Edit")
        {

           // Code to execute when clicked on This Item
        	Bundle v = new Bundle();
        	String value = ""+ p ;
        	v.putString("clkpos", value);
        
        Intent w = new Intent("com.acadplnr.NotesUpdate");
        	w.putExtras(v);
   		startActivity(w);
         } 
        else if(item.getTitle()=="Delete")
        {
        	AlertDialog.Builder adb = new AlertDialog.Builder(
        			Notes.this);
        	        adb.setTitle("Delete Course");
        			adb.setMessage("Are u sure to delete " 
        			+ nl.get(info.position) + " ?" );
        			adb.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Notes_database info = new Notes_database(Notes.this);
							info.open();
							info.deleteEntry(p);
							
							info.close();
							Global1.tabN =1;
							Intent i = new Intent(Notes.this,MainActivity.class);
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
