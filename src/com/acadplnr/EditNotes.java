package com.acadplnr;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditNotes extends Activity implements OnClickListener{
	EditText nTitle;
	Global1 globe;
	EditText nBody;
	Button done;
	String n_title,n_body;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_notes);
		
		final Animation animation = new AlphaAnimation(1, 0); 
		animation.setDuration(500);
		animation.setInterpolator(new LinearInterpolator());
		 animation.setRepeatCount(Animation.INFINITE);
		 animation.setRepeatMode(Animation.REVERSE);
		 
		 
		nTitle = (EditText) findViewById(R.id.notesTitle);
		nBody = (EditText) findViewById(R.id.notesBody);
		done = (Button) findViewById(R.id.baddNotes);
		
		done.startAnimation(animation);
		done.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 v.clearAnimation();
		
		n_title= nTitle.getText().toString();
		n_body=nBody.getText().toString();
		Notes_database info = new Notes_database(this);
		info.open();
		try{
		info.createEntry(n_title, n_body);
		info.KeyRowIdUpdate();
		}
		catch (Exception e)
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
		Global1.tabN=1;
		Intent bablu = new Intent(EditNotes.this,MainActivity.class);
		bablu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(bablu);
	}
     
}
