package com.acadplnr;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NotesDisplay extends Activity implements OnClickListener{

	TextView notesTitle,notesBody;
	Button editNotesDisplay;
	public String ClickedPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notes_display);
		editNotesDisplay = (Button) findViewById(R.id.edbDisplay);
		editNotesDisplay.setOnClickListener(this);
		
		notesTitle = (TextView) findViewById(R.id.TITLE);
		notesBody = (TextView) findViewById(R.id.BODY);
		
		Bundle gotBasket = getIntent().getExtras();
		ClickedPosition = gotBasket.getString("position");
		
		long l = Long.parseLong(ClickedPosition);
		Notes_database info = new Notes_database(this);
		try{
		info.open();
		String all_courses = info.GetNotesDetailById(l);
		String[] ndata = all_courses.split("-");
		
		
		notesTitle.setText(ndata[0]);
		notesBody.setText(ndata[1]);
		}catch (Exception e)
		{
			String error = e.toString();
			Dialog h = new Dialog(this);
			h.setTitle(" :(");
			TextView tv1 = new TextView(this);
			tv1.setText(error);
			h.setContentView(tv1);
			h.show();
		}
		info.close();
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Bundle o = new Bundle();
		String value =  ClickedPosition ;
    	o.putString("clkpos", value);
		Intent w = new Intent("com.acadplnr.NotesUpdate");
        w.putExtras(o);
        
        try{
   		startActivity(w);
        }
        catch(Exception e){
	
			String error = e.toString();
			Dialog h = new Dialog(this);
			h.setTitle(" :(");
			TextView tv1 = new TextView(this);
			tv1.setText(error);
			h.setContentView(tv1);
			h.show();
		}
        finish();
	}
      
}
