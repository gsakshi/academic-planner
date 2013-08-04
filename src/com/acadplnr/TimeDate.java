package com.acadplnr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class TimeDate extends Activity implements OnClickListener {

	int hr, min, hr2, min2;
	TimePicker timepick, timepick2;
	CheckBox cbmon, cbtue, cbwed, cbthu, cbfri, cbsat, cbsun;
	EditText venue;
	Button bdone,cancel;
	String days = "", tim = "", tim2 = "";
	Time t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timedate);
		t = new Time();
		t.setToNow();
		hr = t.hour;
		min = t.minute;
		hr2 = hr + ((min + 50) / 60);
		min2 = (min + 50) % 60;
		//timepick = (TimePicker) findViewById(R.id.time);
		//timepick2 = (TimePicker) findViewById(R.id.time2);
		//timepick.setIs24HourView(true);
		//timepick2.setIs24HourView(true);

		//timepick2.setCurrentHour(hr2);
		//timepick2.setCurrentMinute(min2);
		cbmon = (CheckBox) findViewById(R.id.mon);
		cbtue = (CheckBox) findViewById(R.id.tue);
		cbwed = (CheckBox) findViewById(R.id.wed);
		cbthu = (CheckBox) findViewById(R.id.thu);
		cbfri = (CheckBox) findViewById(R.id.fri);
		//cbsat = (CheckBox) findViewById(R.id.sat);
		//cbsun = (CheckBox) findViewById(R.id.sun);
		venue = (EditText) findViewById(R.id.venue);
		bdone = (Button) findViewById(R.id.done);
		cancel = (Button) findViewById(R.id.cancel);
		//timepick.setOnTimeChangedListener(this);
		//timepick2.setOnTimeChangedListener(this);
		bdone.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		days = "";
		tim="";
		tim2="";
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.done:
			if (cbmon.isChecked())
				days = days + "M ";
			if (cbtue.isChecked())
				days = days + "T ";
			if (cbwed.isChecked())
				days = days + "W ";
			if (cbthu.isChecked())
				days = days + "t ";
			if (cbfri.isChecked())
				days = days + "F ";
			/*if (cbsat.isChecked())
				days = days + "S ";
			if (cbsun.isChecked())
				days = days + "s ";
			if (hr2 < hr || ((hr == hr2) && (min2 < min))) {
				Toast t = Toast.makeText(TimeDate.this,"ending time should be greater than beginning time, enter time again",
								Toast.LENGTH_LONG);
				t.show();
				break;
			}
			tim = tim + hr + ":" + min;
			tim2 = tim2 + hr2 + ":" + min2;*/
			Bundle basket = new Bundle();
			/*basket.putString("start_time", tim);
			basket.putString("end_time", tim2);*/
			basket.putString("day", days);
			basket.putString("ven", venue.getText().toString());
			Intent i = new Intent("");
			i.putExtras(basket);
			setResult(RESULT_OK, i);
			finish();
			break;
		case R.id.cancel :
			finish();
		}

	}

	/*@Override
	public void onTimeChanged(TimePicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		//case R.id.time:
			hr = timepick.getCurrentHour();
			min = timepick.getCurrentMinute();
			hr2 = hr + ((min + 50) / 60);
			min2 = (min + 50) % 60;
			timepick2.setCurrentHour(hr2);
			timepick2.setCurrentMinute(min2);
			hr2 = timepick2.getCurrentHour();
			min2 = timepick2.getCurrentMinute();
			break;
		//case R.id.time2:
			hr2 = timepick2.getCurrentHour();
			min2 = timepick2.getCurrentMinute();
			hr = timepick.getCurrentHour();
			min = timepick.getCurrentMinute();
		}
	}*/

}