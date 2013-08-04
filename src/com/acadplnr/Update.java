package com.acadplnr;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Update extends Activity implements OnClickListener {

	Button bdone, blab;
	String ClickedPosition, lecv, tutv, labd, labv, hdata, welink, profid,
			credits, profname1="",profname2="", profnames[]={},office, insname, prof="";
	Global1 globe;
	EditText courseLnk, instructname, off_hr_add, courseCredits, lectVenue,
			tutVenue, profemail;
	Button bLab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editadd);

		profemail = (EditText) findViewById(R.id.instructorEmailid);
		courseCredits = (EditText) findViewById(R.id.courseCredits);
		lectVenue = (EditText) findViewById(R.id.lecturevenue);
		tutVenue = (EditText) findViewById(R.id.tutorialvenue);
		courseLnk = (EditText) findViewById(R.id.courselink);
		instructname = (EditText) findViewById(R.id.instructorname);
		off_hr_add = (EditText) findViewById(R.id.officehour_and_address);
		bdone = (Button) findViewById(R.id.bdoneeditadd);
		bLab = (Button) findViewById(R.id.labschedule);
		bLab.setOnClickListener(this);
		bdone.setOnClickListener(this);

		Bundle gotBasket = getIntent().getExtras();
		ClickedPosition = gotBasket.getString("clkpos");

		Crs_database_help info = new Crs_database_help(this);

		info.open();
		String RowId = ClickedPosition;
		long l = Long.parseLong(RowId);
		String c = info.GetCourseDetailById(l);

		if (c != null) {

			String[] data;
			String delimiter = "---";
			data = c.split(delimiter);
			prof=data[1];
			profnames = prof.split(",");
	    	for (int i = 0; i < profnames.length; i++) {
	    			if (i == 0)
	    				profname1 = profnames[i];

	    			else if (i == 1)
	    				profname2 = profnames[i];
	    	}
			instructname.setText(profname1 +"\n" + profname2);
			profemail.setText(data[2]);
			courseLnk.setText(data[3]);
			off_hr_add.setText(data[4]);
			courseCredits.setText(data[10]);
			lectVenue.setText(data[6]);
			tutVenue.setText(data[8]);
			labd = data[5];
			labv = data[7];

		}
		info.close();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.bdoneeditadd:

			/*
			 * GetMethodEx test = new GetMethodEx(welink); try{ hdata =
			 * test.getInternetData(); }catch(Exception e){ e.printStackTrace();
			 * String error = e.toString(); Dialog h = new Dialog(this);
			 * h.setTitle(" :("); TextView tv1 = new TextView(this);
			 * tv1.setText(error); h.setContentView(tv1); h.show(); hdata =
			 * "Could not retrieve data from internet"; }
			 */
			boolean didItWORK = true;

			try {
				welink = courseLnk.getText().toString();
				insname = instructname.getText().toString();
				office = off_hr_add.getText().toString();
				profid = profemail.getText().toString();
				credits = courseCredits.getText().toString();
				lecv = lectVenue.getText().toString();
				tutv = tutVenue.getText().toString();
				Crs_database_help entry = new Crs_database_help(Update.this);
				entry.open();
				long l = Long.parseLong(ClickedPosition);
				entry.UpdateCourse(l, insname, welink, office, labd, lecv,
						labv, tutv, profid, credits);
				entry.close();
			} catch (Exception e) {
				didItWORK = false;
				String error = e.toString();
				Dialog h = new Dialog(this);
				h.setTitle(" Course Edit Unsuccessful");
				TextView tv1 = new TextView(this);
				tv1.setText(error);
				h.setContentView(tv1);
				h.show();
			} finally {
				if (didItWORK) {
					Dialog d = new Dialog(this);
					d.setTitle("Course Edited Successfully!");
					TextView tv = new TextView(this);
					tv.setText("Success!");
					d.setContentView(tv);
					d.show();
					Intent i = new Intent(Update.this, MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					
				}
			}
			break;

		case R.id.labschedule:
			if (labd.contentEquals("")) {
				Toast t =Toast.makeText(Update.this, "No lab for this course",
						Toast.LENGTH_LONG);
				t.show();
				
			} else {
				Bundle lab = new Bundle();
				lab.putString("ven", labv);
				lab.putString("day", labd);
				try {
					Intent i1 = new Intent("com.acadplnr.Timeedit");
					i1.putExtras(lab);
					startActivityForResult(i1, 1);
				} catch (Exception e) {
					String error = e.toString();
					Dialog h = new Dialog(this);
					h.setTitle(" ERROR ");
					TextView tv1 = new TextView(this);
					tv1.setText(error);
					h.setContentView(tv1);
					h.show();
				}
				
			}
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Bundle received_basket = data.getExtras();
			if (requestCode == 1) {
				labv = "" + received_basket.getString("ven");
				labd = "" + received_basket.getString("day");
			}
		}

	}
}