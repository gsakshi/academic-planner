package com.acadplnr;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.FeatureInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SlidingDrawer;
import android.widget.TextView;



public class Examscheduler extends Activity implements OnClickListener{

	Button b,openbutton;
	TextView t;
	String cname = "", date = "", day = "", st = "", et = "", schedule = "",
			time = "";
	private String rollno;
	EditText etrollno;
	SlidingDrawer opendrawer;
	Document doc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam_scheduler);
		b = (Button) findViewById (R.id.roll);
		etrollno =(EditText) findViewById(R.id.editText1);
		b.setOnClickListener(this);
		t = (TextView) findViewById(R.id.examdetail);
		opendrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer1);
		ExamDatabase entry = new ExamDatabase(Examscheduler.this);
		entry.open();
		if(entry.getCount()>0)
		{
		String data= entry.getdata();
		entry.close();
		t.setText(data);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	
	@Override
	public void onClick(View ABCD) {
		
		switch (ABCD.getId()) {
		case R.id.roll:
			
			rollno = etrollno.getText().toString();
			opendrawer.close();
			

			try {
			
				 class fetch extends AsyncTask<Void, Void, Void>
			    {

			        ProgressDialog mProgressDialog;
			        @Override
			        protected void onPostExecute(Void result) {
			            mProgressDialog.dismiss();
			            Elements tablerows = doc.select("tr");
						ExamDatabase entry = new ExamDatabase(Examscheduler.this);
						entry.open();
						
						if(entry.getCount()>0)
						{
							//delete_allevents_by_id();
							entry.deleteEntry();
						}
						for (Element row : tablerows) {

							Elements tds = row.select("td");
							if (tds.get(0).text().contentEquals("COURSE"))
								continue;
							cname = tds.get(0).text();
							schedule = tds.get(1).text();
							time = tds.get(2).text();
							
							breakschedule();
							breaktime();
							
							try {
								
								
								long l = syncCalendar(cname, date, day,st, et);
								entry.createEntry(cname,date,day,st,et,""+l);
								
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						entry.KeyRowIdUpdate();
						
						String data= entry.getdata();
						entry.close();
						t.setText(data);
			       
			        }

			        @Override
			        protected void onPreExecute() {
			            mProgressDialog = ProgressDialog.show(Examscheduler.this, 
			                                            "Loading...", "Data is Loading...");
			        }

			        @Override
			        protected Void doInBackground(Void... params) {
			           // your network operation
			        try {
						final Document	doc1 = Jsoup
									.connect(
											"http://172.26.142.68/examscheduler2/personal_schedule.php?rollno="+rollno)
									.get();
						doc = doc1;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        	
			            return null;
			        }
			    }
				new fetch().execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				String error = e.toString();
				Dialog h = new Dialog(this);
				h.setTitle(" :(");
				TextView tv1 = new TextView(this);
				tv1.setText(error);
				h.setContentView(tv1);
				h.show();
				break;
			}
			

			

			break;
			
		}
		
	}

	public void breakschedule() {
		date="";day="";
		int j = 0, k = 0;
		while (!((schedule.charAt(j) >= '0' && schedule.charAt(j) <= '9') || schedule.charAt(j) == 'N')) {
			j++;
		}
		if (schedule.charAt(j) == 'N')
			date = "NA";
		else {
			for (k = j; k < j + 8; k++) {
				date = date + schedule.charAt(k);
			}
		}
		while (!(schedule.charAt(k) == '(' || schedule.charAt(k) == 'L')) {
			k++;
		}
		if (schedule.charAt(k) == '(') {
			k++;
			for (int i = 1; i <= 3; i++, k++)
				day = day + schedule.charAt(k);
		} else {
			day = "NA";
		}

	}

	public void breaktime() {

		st="";et="";
		int p = 0;
		while (!((time.charAt(p) >= '0' && time.charAt(p) <= '9') || time.charAt(p) == 'N')) {
			p++;
		}
		if (time.charAt(p) == 'N') {
			st = "NA";
			et = "NA";
		} else {

			while (time.charAt(p) != '-') {
				st = st + time.charAt(p);
				p++;
			}
			while (!(time.charAt(p) >= '0' && time.charAt(p) <= '9')) {
				p++;
			}

			while (p < time.length()) {
				et = et + time.charAt(p);
				p++;
			}
		}
	}
	public long syncCalendar(String courseNo,String date,String day,String st,String ET) throws InterruptedException{
		
		if(st.contentEquals("NA")||ET.contentEquals("NA")||date.contentEquals("NA"))
		{
			return 0;
		}
		String[] DATE = date.split("/");
		long D1 = Long.parseLong(DATE[0]);
		long D2 = Long.parseLong(DATE[1]);
		long D3 = Long.parseLong("20"+DATE[2]);
		String[] START = st.split(":");
		long S1 = Long.parseLong(START[0]);
		long S2 = Long.parseLong(START[1]);
		String[] END = ET.split(":");
		long E1 = Long.parseLong(END[0]);
		long E2 = Long.parseLong(END[1]);
		long calID = 1;
		long startMillis = 0; 
		long endMillis = 0; 
		try{
			
		Calendar beginTime = Calendar.getInstance();
		beginTime.set((int)D3,(int)D2-1,(int)D1 ,(int)S1,(int)S2);
		startMillis = beginTime.getTimeInMillis();
		Calendar endTime = Calendar.getInstance();
		endTime.set((int)D3,(int)D2 -1 ,(int)D1 ,(int)E1,(int)E2);
		endMillis = endTime.getTimeInMillis();
		}
		catch(Exception e){
			String error = e.toString();
			Dialog h = new Dialog(this);
			h.setTitle(" :(");
			TextView tv1 = new TextView(this);
			tv1.setText(error);
			h.setContentView(tv1);
			h.show();
			wait(5000);
		}
		
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		values.put("dtstart", startMillis);
		values.put("dtend", endMillis);
		values.put("title", "EXAM "+courseNo);
		values.put("description", courseNo);
		values.put("calendar_id", calID);
		values.put( "eventTimezone", ""+TimeZone.getDefault());
		Uri baseUri;
	    if (Build.VERSION.SDK_INT >= 8) {
	        baseUri = Uri.parse("content://com.android.calendar/events");
	    } else {
	        baseUri = Uri.parse("content://calendar/events");
	    }
		try{
		Uri uri = cr.insert(baseUri, values);

		// get the event ID that is the last element in the Uri
		long eventID = Long.parseLong(uri.getLastPathSegment());
		return eventID;
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
		return 0;
	}
	public void delete_allevents_by_id(){
		ExamDatabase exam = new ExamDatabase(Examscheduler.this);
		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		Uri deleteUri = null;
		Uri baseUri;
	    if (Build.VERSION.SDK_INT >= 8) {
	        baseUri = Uri.parse("content://com.android.calendar/events");
	    } else {
	        baseUri = Uri.parse("content://calendar/events");
	    }
		

		exam.open();
		
		try{
		for(int i=0;i<exam.getCount();i++)
		{
			long eventID = Long.parseLong(exam.GetCourseIDById(i+1));
			if(eventID==0)
			{
				continue;
			}
			deleteUri = ContentUris.withAppendedId(baseUri, eventID);
			int rows = getContentResolver().delete(deleteUri, null, null);
		}
		}
		catch(Exception e)
		{
			String error = e.toString();
			Dialog h = new Dialog(this);
			h.setTitle(" :(");
			TextView tv1 = new TextView(this);
			tv1.setText(error);
			h.setContentView(tv1);
			h.show();
		}
		exam.close();
	}
}
