package com.acadplnr;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class Timeedit extends Activity implements OnClickListener{
	
	
	//long hr, min, hr2, min2;
	//int hri, mini, hr2i, min2i, i;
	//TimePicker timepick, timepick2;
	CheckBox cbmon, cbtue, cbwed, cbthu, cbfri;
	EditText venue;
	Button bdone,cancel;
	String days,daysi, v, vi, daysf[];
	Bundle gotbasket;
	int i;
	//Time t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timedate);
		gotbasket = getIntent().getExtras();
		days = gotbasket.getString("day");
		//tim = gotbasket.getString("start");
		//tim2 =  gotbasket.getString("end");
		v =  gotbasket.getString("ven");
		
		//timef=tim.split(":");
		//timef2=tim2.split(":");
		daysf=days.split(" ");
		
		/*if(timef[0].contentEquals("NotYetSet")){
		t = new Time();
		t.setToNow();
		hr = t.hour;
		min = t.minute;
		hr2 = hr + ((min + 50) / 60);
		min2 = (min + 50) % 60;
		}
		else{
			hr = Long.parseLong(timef[0]);
			min = Long.parseLong(timef[1]);
			hr2 = Long.parseLong(timef2[0]);
			min2 = Long.parseLong(timef2[1]);
		}
		
		hri=(int)hr;
		hr2i=(int)hr2;
		mini=(int)min;
		min2i=(int)min2;
		timepick = (TimePicker) findViewById(R.id.time);
		timepick2 = (TimePicker) findViewById(R.id.time2);
		timepick.setIs24HourView(true);
		timepick2.setIs24HourView(true);
		timepick.setCurrentHour(hri);
		timepick.setCurrentMinute(mini);
		timepick2.setCurrentHour(hr2i);
		timepick2.setCurrentMinute(min2i);
		*/
		
		cbmon = (CheckBox) findViewById(R.id.mon);
		cbtue = (CheckBox) findViewById(R.id.tue);
		cbwed = (CheckBox) findViewById(R.id.wed);
		cbthu = (CheckBox) findViewById(R.id.thu);
		cbfri = (CheckBox) findViewById(R.id.fri);
		venue = (EditText) findViewById(R.id.venue);
		bdone = (Button) findViewById(R.id.done);
		cancel = (Button) findViewById(R.id.cancel);
		//timepick.setOnTimeChangedListener(this);
		//timepick2.setOnTimeChangedListener(this);
		bdone.setOnClickListener(this);
		cancel.setOnClickListener(this);

		//for(i=0;i<daysf.length;i++)
		//{
			if(days.contains("M")){
				cbmon.setChecked(true);
			}
			if(days.contains("T ")){
				cbtue.setChecked(true);
			}
			if(days.contains("W")){
				cbwed.setChecked(true);
			}
			if(days.contains("Th")){
				cbthu.setChecked(true);
			}
			if(days.contains("F")){
				cbfri.setChecked(true);
			}
						
		//}
		venue.setText(v);
		
	}

	@Override
	public void onClick(View arg0) {
		
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.done:
			daysi="";
			//timi="";
			//tim2i="";
			if (cbmon.isChecked())
				daysi = daysi + "M ";
			if (cbtue.isChecked())
				daysi = daysi + "T ";
			if (cbwed.isChecked())
				daysi = daysi + "W ";
			if (cbthu.isChecked())
				daysi = daysi + "Th ";
			if (cbfri.isChecked())
				daysi = daysi + "F ";
			/*if (hr2 < hr || ((hr == hr2) && (min2 < min))) {
				Toast t = Toast.makeText(Timeedit.this,"ending time should be greater than beginning time, enter time again",
								Toast.LENGTH_LONG);
				t.show();
				break;
			}*/
			//timi = timi + hr + ":" + min;
			//tim2i = tim2i + hr2 + ":" + min2;
			Bundle basket = new Bundle();
			//basket.putString("start_time", timi);
			//basket.putString("end_time", tim2i);
			basket.putString("day", daysi);
			basket.putString("ven", venue.getText().toString());
			Intent i = new Intent("");
			i.putExtras(basket);
			setResult(RESULT_OK, i);
			finish();
			break;
		case R.id.cancel:
			try{
				
				finish();
				break;
			
			}catch(Exception e){
				
				String error = e.toString();
				Dialog h = new Dialog(this);
				h.setTitle(" :(");
				TextView tv1 = new TextView(this);
				tv1.setText(error);
				h.setContentView(tv1);
				h.show();
			}
		}

	}
	

	/*@Override
	public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.time:
			hr = timepick.getCurrentHour();
			min = timepick.getCurrentMinute();
			hr2 = hr + ((min + 50) / 60);
			min2 = (min + 50) % 60;
			hr2i = (int)hr2;
			min2i = (int)min2;
			timepick2.setCurrentHour(hr2i);
			timepick2.setCurrentMinute(min2i);
			break;
		case R.id.time2:
			hr2 = timepick2.getCurrentHour();;
			min2 = timepick2.getCurrentMinute();
			
		}
	}*/


	
	
	

}
