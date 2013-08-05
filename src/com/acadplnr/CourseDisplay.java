package com.acadplnr;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CourseDisplay extends Activity implements OnClickListener {
	Global1 globe;
	Button beditcourse;
	TextView courseNm, courseLnk, instructorNm, courseEmailId, courseCredits,
			courseSchedule, off_hr_add;
	public String ClickedPosition, CourseLink, SUDOschedule = "";
	String html, coursetitle = "", prof = "", schedule = "", profname[] = {},
			profname1 = "", profname2 = "",profn="", profemail = "", lecv = "",
			tutv = "", labv = "", lecdays = "", tutdays = "", labdays = "",
			weblink = "", lecmonst = "", lecmonet = "", lectuest = "",
			lectueet = "", offhradd = "", lecwedst = "", lecwedet = "",
			lecthust = "", lecthuet = "", lecfrist = "", lecfriet = "",
			lecsatst = "", lecsatet = "", lecsunst = "", lecsunet = "",
			tutmonst = "", tutmonet = "", tuttuest = "", tuttueet = "",
			tutwedst = "", tutwedet = "", tutthust = "", tutthuet = "",
			tutfrist = "", tutfriet = "", tutsatst = "", tutsatet = "",
			tutsunst = "", tutsunet = "", labmonst = "", labmonet = "",
			labtuest = "", labtueet = "", labwedst = "", labwedet = "",
			labthust = "", labthuet = "", labfrist = "", labfriet = "",
			labsatst = "", labsatet = "", labsunst = "", labsunet = "",
			credits = "", lecvf = "", labvf = "", tutvf = "", labdaysf = "";
	int j;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coursedisplay);
		beditcourse = (Button) findViewById(R.id.editbutton);
		beditcourse.setOnClickListener(this);

		courseNm = (TextView) findViewById(R.id.coursename);
		courseLnk = (TextView) findViewById(R.id.courseLink);
		instructorNm = (TextView) findViewById(R.id.instructor_name);
		courseEmailId = (TextView) findViewById(R.id.email_id);
		courseCredits = (TextView) findViewById(R.id.courseCredits);
		courseSchedule = (TextView) findViewById(R.id.schedule);
		off_hr_add = (TextView) findViewById(R.id.office_hr_add);

		// lec_et = (TextView) findViewById(R.id.add_lecture_endtime);
		// tut_et = (TextView) findViewById(R.id.add_tutorial_endtime);
		// lab_et = (TextView) findViewById(R.id.add_lab_endtime);

		courseLnk.setClickable(true);
		courseLnk.setOnClickListener(this);
		//courseEmailId.setClickable(true);
		//courseEmailId.setOnClickListener(this);

		Bundle gotBasket = getIntent().getExtras();
		ClickedPosition = gotBasket.getString("position");

		beditcourse.setOnClickListener(this);

		long l = Long.parseLong(ClickedPosition);
		Crs_database_help info = new Crs_database_help(this);
		try {
			info.open();
			String all_courses = info.GetCourseDetailById(l);
			String[] data = all_courses.split("---");

			CourseLink = data[3];
			courseNm.setText(data[0]);
			courseLnk.setText(data[3]);
			schedule = data[9];
			lecvf = data[6];
			labvf = data[7];
			tutvf = data[8];
			labdaysf = data[5];
			breakschedule();
			prof = data[1];
			profname = prof.split(", ");
			for (int i = 0; i < profname.length; i++) {
				if (i == 0)
					profn = profname[i];

				else if (i == 1)
					profn = profn  + profname[i];
			}
			instructorNm.setText(profn);
			courseEmailId.setText(data[2]);
			off_hr_add.setText(data[4]);
			courseCredits.setText(data[10]);

			get_sudo_schedule();
			courseSchedule.setText(SUDOschedule);

		} catch (Exception e) {
			String error = e.toString();
			Dialog h = new Dialog(this);
			h.setTitle(" :(....cd");
			TextView tv1 = new TextView(this);
			tv1.setText(error);
			h.setContentView(tv1);
			h.show();
		}
		info.close();

	}

	private void get_sudo_schedule() {
		// TODO Auto-generated method stub
		int tutflag = 0, lecflag = 0, labflag = 0;
		if (lecmonst != "") {
			if (lecflag == 0) {
				SUDOschedule = SUDOschedule + "Lectures :" + "\n";
			}
			SUDOschedule = SUDOschedule + "M" + "  : " + lecmonst + " to "
					+ lecmonet + "\n";
			lecflag = 1;
		}
		if (lectuest != "") {
			if (lecflag == 0) {
				SUDOschedule = SUDOschedule + "Lectures :" + "\n";
			}
			SUDOschedule = SUDOschedule + "T" + "  : " + lectuest + " to "
					+ lectueet + "\n";
			lecflag = 1;
		}
		if (lecwedst != "") {
			if (lecflag == 0) {
				SUDOschedule = SUDOschedule + "Lectures :" + "\n";
			}
			SUDOschedule = SUDOschedule + "W" + "  : " + lecwedst + " to "
					+ lecwedet + "\n";
			lecflag = 1;
		}
		if (lecthust != "") {
			if (lecflag == 0) {
				SUDOschedule = SUDOschedule + "Lectures :" + "\n";
			}
			SUDOschedule = SUDOschedule + "Th" + " : " + lecthust + " to "
					+ lecthuet + "\n";
			lecflag = 1;
		}
		if (lecfrist != "") {
			if (lecflag == 0) {
				SUDOschedule = SUDOschedule + "Lectures :" + "\n";
			}
			SUDOschedule = SUDOschedule + "F" + "  : " + lecfrist + " to "
					+ lecfriet + "\n";
			lecflag = 1;
		}

		if (lecflag == 1 && !lecvf.contentEquals("N.A.")) {
			SUDOschedule = SUDOschedule + "Lecture Venue: " + lecvf + "\n";
		}
		SUDOschedule = SUDOschedule + "\n";

		if (tutmonst != "") {
			if (tutflag == 0) {
				SUDOschedule = SUDOschedule + "Tutorials :" + "\n";
			}
			SUDOschedule = SUDOschedule + "M" + "  : " + tutmonst + " to "
					+ tutmonet + "\n";
			tutflag = 1;
		}
		if (tuttuest != "") {
			if (tutflag == 0) {
				SUDOschedule = SUDOschedule + "Tutorials :" + "\n";
			}
			SUDOschedule = SUDOschedule + "T" + "  : " + tuttuest + " to "
					+ tuttueet + "\n";
			tutflag = 1;
		}
		if (tutwedst != "") {
			if (tutflag == 0) {
				SUDOschedule = SUDOschedule + "Tutorials :" + "\n";
			}
			SUDOschedule = SUDOschedule + "W" + "  : " + tuttuest + " to "
					+ tuttueet + "\n";
			tutflag = 1;
		}
		if (tutthust != "") {
			if (tutflag == 0) {
				SUDOschedule = SUDOschedule + "Tutorials :" + "\n";
			}
			SUDOschedule = SUDOschedule + "Th" + " : " + tutthust + " to "
					+ tutthuet + "\n";
			tutflag = 1;
		}
		if (tutfrist != "") {
			if (tutflag == 0) {
				SUDOschedule = SUDOschedule + "Tutorials :" + "\n";
			}
			SUDOschedule = SUDOschedule + "F" + "  : " + tutfrist + " to "
					+ tutfriet + "\n";
			tutflag = 1;
		}

		if (tutflag == 1 && !tutvf.contentEquals("N.A.")) {
			if (tutflag == 0) {
				SUDOschedule = SUDOschedule + "Tutorials :" + "\n";
			}
			SUDOschedule = SUDOschedule + "Tutorial Venue: " + tutvf + "\n";
		}
		SUDOschedule = SUDOschedule + "\n";
		if (labdaysf.contains("M")) {
			if (labflag == 0) {
				SUDOschedule = SUDOschedule + "Labs :" + "\n";
			}
			SUDOschedule = SUDOschedule + "M" + "  : " + labmonst + " to "
					+ labmonet + "\n";
			labflag = 1;
		}
		if (labdaysf.contains("T ")) {
			if (labflag == 0) {
				SUDOschedule = SUDOschedule + "Labs :" + "\n";
			}
			SUDOschedule = SUDOschedule + "T" + "  : " + labtuest + " to "
					+ labtueet + "\n";
			labflag = 1;
		}
		if (labdaysf.contains("W")) {
			if (labflag == 0) {
				SUDOschedule = SUDOschedule + "Labs :" + "\n";
			}
			SUDOschedule = SUDOschedule + "W" + "  : " + labwedst + " to "
					+ labwedet + "\n";
			labflag = 1;
		}
		if (labdaysf.contains("Th")) {
			if (labflag == 0) {
				SUDOschedule = SUDOschedule + "Labs :" + "\n";
			}
			SUDOschedule = SUDOschedule + "Th" + " : " + labthust + " to "
					+ labthuet + "\n";
			labflag = 1;
		}
		if (labdaysf.contains("F")) {
			if (labflag == 0) {
				SUDOschedule = SUDOschedule + "Labs :" + "\n";
			}
			SUDOschedule = SUDOschedule + "F" + "  : " + labfrist + " to "
					+ labfriet + "\n";
			labflag = 1;
		}
		if (labflag == 1 && !labvf.contentEquals("N.A.")) {
			SUDOschedule = SUDOschedule + "Lab Venue: " + labvf;
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.editbutton:
			Bundle v = new Bundle();
			String value = ClickedPosition;
			v.putString("clkpos", value);
			Intent w = new Intent("com.acadplnr.Update");
			w.putExtras(v);

			try {
				startActivity(w);
			} catch (Exception e) {

				String error = e.toString();
				Dialog h = new Dialog(this);
				h.setTitle(" :(");
				TextView tv1 = new TextView(this);
				tv1.setText(error);
				h.setContentView(tv1);
				h.show();
			}
			break;
		case R.id.courseLink:
			String url = CourseLink;
			if (!url.startsWith("http://") && !url.startsWith("https://"))
				url = "http://" + url;
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
			break;
		/*case R.id.email_id:
			String email = courseEmailId.getText().toString();
			String emailadd[] = {email};
			Intent emailintent = new Intent(android.content.Intent.ACTION_SEND);
			//emailintent.putExtra(android.content.Intent.EXTRA_EMAIL, emailadd);
			startActivity(emailintent);
			break;*/
		}
		
	}

	public void breakschedule() {

		int lecindex, tutindex, labindex;
		lecindex = schedule.indexOf("LEC");
		tutindex = schedule.indexOf("TUT");
		labindex = schedule.indexOf("LAB");

		j = 0;
		if (lecindex != -1) {

			String lecd = "", lect = "", lecvenue = "";
			j = lecindex + 5;
			skip_white_spaces(schedule);
			while (((tutindex != -1 && j < tutindex) || tutindex == -1)
					&& ((labindex != -1 && j < labindex) || labindex == -1)
					&& j < schedule.length() && schedule.charAt(j) != 'n') {

				lecd = "";
				lect = "";
				lecvenue = "";
				while (schedule.charAt(j) != ' ' && j < schedule.length()) {
					lecd = lecd + schedule.charAt(j);
					j++;
				}
				j++;

				while (schedule.charAt(j) != ' ' && j < schedule.length()) {
					lect = lect + schedule.charAt(j);
					j++;
				}

				j++;

				while (schedule.charAt(j) != ';' && j < schedule.length()) {
					lecvenue = lecvenue + schedule.charAt(j);
					j++;
				}
				j++;
				if (j < schedule.length())
					skip_white_spaces(schedule);
				if (lecvenue.contentEquals("REQ")) {
					if (lecv.contentEquals(""))
						lecv = "N.A.";
				} else
					lecv = lecvenue;

				if (lecd.contains("M")) {
					lecdays = lecdays + "M ";
					lecmonst = lect.split("-")[0];
					lecmonet = lect.split("-")[1];
				}
				if (lecd.contains("T")
						&& (((lecd.indexOf("T") + 1) < lecd.length() && lecd
								.charAt(lecd.indexOf("T") + 1) != 'h') || (lecd
								.indexOf("T") + 1) == lecd.length())) {
					lecdays = lecdays + "T ";
					lectuest = lect.split("-")[0];
					lectueet = lect.split("-")[1];
				}
				if (lecd.contains("W")) {
					lecdays = lecdays + "W ";
					lecwedst = lect.split("-")[0];
					lecwedet = lect.split("-")[1];
				}
				if (lecd.contains("Th")) {
					lecdays = lecdays + "Th ";
					lecthust = lect.split("-")[0];
					lecthuet = lect.split("-")[1];
				}
				if (lecd.contains("F")) {
					lecdays = lecdays + "F ";
					lecfrist = lect.split("-")[0];
					lecfriet = lect.split("-")[1];
				}

			}
		}
		j = 0;
		if (tutindex != -1) {

			String tutd = "", tutt = "", tutvenue = "";
			j = tutindex + 5;
			skip_white_spaces(schedule);
			while (((labindex != -1 && j < labindex) || labindex == -1)
					&& j < schedule.length() && schedule.charAt(j) != 'n') {
				tutd = "";
				tutt = "";
				tutvenue = "";
				while (schedule.charAt(j) != ' ' && j < schedule.length()) {
					tutd = tutd + schedule.charAt(j);
					j++;
				}
				j++;

				while (schedule.charAt(j) != ' ' && j < schedule.length()) {
					tutt = tutt + schedule.charAt(j);
					j++;
				}
				j++;

				while (schedule.charAt(j) != ';' && j < schedule.length()) {
					tutvenue = tutvenue + schedule.charAt(j);
					j++;
				}
				j++;

				if (j < schedule.length())
					skip_white_spaces(schedule);
				if (tutvenue.contentEquals("REQ")) {
					if (tutv.contentEquals(""))
						tutv = "N.A.";
				} else
					tutv = tutvenue;

				if (tutd.contains("M")) {
					tutdays = tutdays + "M ";
					tutmonst = tutt.split("-")[0];
					tutmonet = tutt.split("-")[1];
				}
				if (tutd.contains("T")
						&& (((tutd.indexOf("T") + 1) < tutd.length() && tutd
								.charAt(tutd.indexOf("T") + 1) != 'h') || (tutd
								.indexOf("T") + 1) == tutd.length())) {
					tutdays = tutdays + "T ";
					tuttuest = tutt.split("-")[0];
					tuttueet = tutt.split("-")[1];
				}
				if (tutd.contains("W")) {
					tutdays = tutdays + "W ";
					tutwedst = tutt.split("-")[0];
					tutwedet = tutt.split("-")[1];
				}
				if (tutd.contains("Th")) {
					tutdays = tutdays + "Th ";
					tutthust = tutt.split("-")[0];
					tutthuet = tutt.split("-")[1];
				}
				if (tutd.contains("F")) {
					tutdays = tutdays + "F ";
					tutfrist = tutt.split("-")[0];
					tutfriet = tutt.split("-")[1];
				}

			}
		}
		j = 0;
		if (labindex != -1) {

			String labd = "", labt = "", labvenue = "";
			j = labindex + 5;
			skip_white_spaces(schedule);
			while (j < schedule.length() && schedule.charAt(j) != 'n') {
				labd = "";
				labt = "";
				labvenue = "";
				while (schedule.charAt(j) != ' ' && j < schedule.length()) {
					labd = labd + schedule.charAt(j);
					j++;
				}
				j++;

				while (schedule.charAt(j) != ' ' && j < schedule.length()) {
					labt = labt + schedule.charAt(j);
					j++;
				}

				j++;
				while (schedule.charAt(j) != ';' && j < schedule.length()) {
					labvenue = labvenue + schedule.charAt(j);
					j++;
				}
				j++;

				if (j < schedule.length())
					skip_white_spaces(schedule);
				if (labvenue.contentEquals("REQ")) {
					if (labv.contentEquals(""))
						labv = "N.A.";
				} else
					labv = labvenue;

				if (labd.contains("M")) {
					labdays = labdays + "M ";
					labmonst = labt.split("-")[0];
					labmonet = labt.split("-")[1];
				}
				if (labd.contains("T")
						&& (((labd.indexOf("T") + 1) < labd.length() && labd
								.charAt(labd.indexOf("T") + 1) != 'h') || (labd
								.indexOf("T") + 1) == labd.length())) {
					labdays = labdays + "T ";
					labtuest = labt.split("-")[0];
					labtueet = labt.split("-")[1];
				}
				if (labd.contains("W")) {
					labdays = labdays + "W ";
					labwedst = labt.split("-")[0];
					labwedet = labt.split("-")[1];
				}
				if (labd.contains("Th")) {
					labdays = labdays + "Th ";
					labthust = labt.split("-")[0];
					labthuet = labt.split("-")[1];
				}
				if (labd.contains("F")) {
					labdays = labdays + "F ";
					labfrist = labt.split("-")[0];
					labfriet = labt.split("-")[1];
				}

			}
		}
	}

	public void skip_white_spaces(String schedule) {

		while (schedule.charAt(j) == ' ' && j < (schedule.length())) {
			j++;
		}
	}
	

}