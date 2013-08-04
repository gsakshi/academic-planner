package com.acadplnr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class noNotes extends Activity{
	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		tv.setText("No Notes Added");
	}
      
}
