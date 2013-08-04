package com.acadplnr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Calendar1 extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		TextView v = (TextView) findViewById(R.id.db1);
		Crs_database_help info = new Crs_database_help(this);
		info.open();
		String data = info.getdata();
		v.setText(data);
		info.close();
	}
}
