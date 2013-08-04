package com.acadplnr;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.widget.TextView;

public class TimeTable extends Activity {

	
	TextView tvMonC, tvMonT, tvTusC, tvTusT, tvWedC, tvWedT, tvThurC, tvThurT,
			tvFriC, tvFriT, tvSatC, tvSatT, tvSunC, tvSunT;
	
	String html, coursetitle = "", prof = "", schedule = "", profname[] = {},
			profname1 = "", profname2 = "", profemail = "", lecv = "",
			tutv = "", labv = "", lecdays = "", tutdays = "", labdays = "",weblink="",
			lecmonst = "", lecmonet = "", lectuest = "", lectueet = "",offhradd="",
			lecwedst = "", lecwedet = "", lecthust = "", lecthuet = "",
			lecfrist = "", lecfriet = "", lecsatst = "", lecsatet = "",
			lecsunst = "", lecsunet = "", tutmonst = "", tutmonet = "",
			tuttuest = "", tuttueet = "", tutwedst = "", tutwedet = "",
			tutthust = "", tutthuet = "", tutfrist = "", tutfriet = "",
			tutsatst = "", tutsatet = "", tutsunst = "", tutsunet = "",
			labmonst = "", labmonet = "", labtuest = "", labtueet = "",
			labwedst = "", labwedet = "", labthust = "", labthuet = "",
			labfrist = "", labfriet = "", labsatst = "", labsatet = "",
			labsunst = "", labsunet = "", smont = "",
					stuet = "", swedt = "", sthut = "", sfrit = "", ssatt = "",
					ssunt = "", smonc = "", stuec = "", swedc = "", sthuc = "",
					sfric = "", ssatc = "", ssunc = "",credits="",SUDOschedule="",LABdays;
	int a=0,b=0,c=0,d=0,e=0,f=0,g=0;
	int j;

	private String courseName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_table);
		tvMonC = (TextView) findViewById(R.id.tvMonC);
		tvTusC = (TextView) findViewById(R.id.tvTusC);
		tvWedC = (TextView) findViewById(R.id.tvWedC);
		tvThurC = (TextView) findViewById(R.id.tvThurC);
		tvFriC = (TextView) findViewById(R.id.tvFriC);
		
		
		tvMonT = (TextView) findViewById(R.id.tvMonT);
		tvTusT = (TextView) findViewById(R.id.tvTusT);
		tvWedT = (TextView) findViewById(R.id.tvWedT);
		tvThurT = (TextView) findViewById(R.id.tvThurT);
		tvFriT = (TextView) findViewById(R.id.tvFriT);
		
		Crs_database_help info = new Crs_database_help(this);
		info.open();
	
			int count = info.getCount();
			for (int j = 0; j < count; j++) {
				String detail = info.GetCourseDetailById(j + 1);
				String data[] = detail.split("---");
				courseName = data[0].split(" ")[0];
				schedule = data[9];
				LABdays = data[5];
				try{
				breakschedule();
				}catch(Exception e)
				{
					String error = e.toString();
					Dialog h = new Dialog(this);
					h.setTitle(" :( break sch");
					TextView tv1 = new TextView(this);
					tv1.setText(error);
					h.setContentView(tv1);
					h.show();
				}
				try{
					get_timetable();
					}catch(Exception e)
					{
						String error = e.toString();
						Dialog h = new Dialog(this);
						h.setTitle(" :( break sch");
						TextView tv1 = new TextView(this);
						tv1.setText(error);
						h.setContentView(tv1);
						h.show();
					}
				
				
			}
			
		
		 tvMonC.setText(smonc); tvMonT.setText(smont);
		 tvTusC.setText(stuec); tvTusT.setText(stuet);
		 tvWedC.setText(swedc); tvWedT.setText(swedt);
		 tvThurC.setText(sthuc); tvThurT.setText(sthut);
		 tvFriC.setText(sfric); tvFriT.setText(sfrit);
		 
		
		 info.close();
	}
	public void breakschedule() {

		int lecindex, tutindex, labindex;
		lecindex = schedule.indexOf("LEC");
		tutindex = schedule.indexOf("TUT");
		labindex = schedule.indexOf("LAB");
		
		j=0;
		if (lecindex != -1) {

			String lecd = "", lect = "", lecvenue = "";
			j = lecindex + 5;
			skip_white_spaces(schedule);
			while (((tutindex!=-1 && j < tutindex) || tutindex==-1) && ((labindex!=-1 && j < labindex) || labindex==-1) && j<schedule.length() && schedule.charAt(j)!='n') {
				
				lecd = ""; lect = ""; lecvenue = "";
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
					lecd = lecd + schedule.charAt(j);
					j++;
				}
				j++;
				
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
						lect = lect + schedule.charAt(j);
						j++;
				}
				
				j++;
				
				while (schedule.charAt(j)!=';' && j<schedule.length() ) {
					lecvenue = lecvenue + schedule.charAt(j);
					j++;
				}
				j++;
				if(j<schedule.length())
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
				if (lecd.contains("T") && (((lecd.indexOf("T") + 1)<lecd.length() && lecd.charAt(lecd.indexOf("T") + 1) != 'h') || (lecd.indexOf("T") + 1)==lecd.length())){
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
				}if (lecd.contains("F")) {
					lecdays = lecdays + "F ";
					lecfrist = lect.split("-")[0];
					lecfriet = lect.split("-")[1];
				}
				
				
			}
		}
		j=0;
		if (tutindex != -1) {

			String tutd = "", tutt = "", tutvenue = "";
			j = tutindex + 5;
			skip_white_spaces(schedule);
			while (((labindex!=-1 && j < labindex) || labindex==-1) && j<schedule.length() && schedule.charAt(j)!='n' ) {
				tutd = ""; tutt = ""; tutvenue = "";
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
					tutd = tutd + schedule.charAt(j);
					j++;
				}
				j++;
				
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
						tutt = tutt + schedule.charAt(j);
						j++;
				}
				j++;
				
				while (schedule.charAt(j) != ';' && j<schedule.length() ) {
					tutvenue = tutvenue + schedule.charAt(j);
					j++;
				}
				j++;
				
				if(j<schedule.length())
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
				if(tutd.contains("T") && (((tutd.indexOf("T") + 1)<tutd.length() && tutd.charAt(tutd.indexOf("T") + 1) != 'h') || (tutd.indexOf("T") + 1)==tutd.length())) {
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
		j=0;
		if (labindex != -1) {

			String labd = "", labt = "", labvenue = "";
			j = labindex + 5;
			skip_white_spaces(schedule);
			while (j<schedule.length() && schedule.charAt(j)!='n') {
				labd = ""; labt = ""; labvenue = "";
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
					labd = labd + schedule.charAt(j);
					j++;
				}
				j++;
				
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
						labt = labt + schedule.charAt(j);
						j++;
				}
				
				j++;
				while (schedule.charAt(j) != ';' && j<schedule.length() ) {
					labvenue = labvenue + schedule.charAt(j);
					j++;
				}
				j++;
				
				if(j<schedule.length())
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
				if (labd.contains("T") && (((labd.indexOf("T") + 1)<labd.length() && labd.charAt(labd.indexOf("T") + 1) != 'h') || (labd.indexOf("T") + 1)==labd.length())) {
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
	
	public void skip_white_spaces(String schedule){
		
		while(schedule.charAt(j)==' ' && j<(schedule.length())){
			j++;
		}
	}
	private void get_timetable() {
		// TODO Auto-generated method stub
		if(lecmonst != "")
		{
			smonc = smonc+ courseName+"(LEC)"+ "\n";
			smont = smont +lecmonst +" to "+ lecmonet + "\n";
		}
		if(lectuest != "")
		{
			stuec = stuec+ courseName+"(LEC)"+ "\n";
			stuet = stuet +lectuest +" to "+ lectueet + "\n";
		}
		if(lecwedst != "")
		{
			swedc = swedc+ courseName+"(LEC)"+ "\n";
			swedt = swedt +lecwedst +" to "+ lecwedet + "\n";
		}
		if(lecthust != "")
		{
			sthuc = sthuc+ courseName+"(LEC)"+ "\n";
			sthut = sthut +lecthust +" to "+ lecthuet + "\n";
		}
		if(lecfrist != "")
		{
			sfric = sfric+ courseName+"(LEC)"+ "\n";
			sfrit = sfrit +lecfrist +" to "+ lecfriet + "\n";
		}
		
		
		if(tutmonst != "")
		{
			smonc = smonc+ courseName+"(TUT)"+ "\n";
			smont = smont +tutmonst +" to "+ tutmonet + "\n";
		}
		if(tuttuest != "")
		{
			stuec = stuec+ courseName+"(TUT)"+ "\n";
			stuet = stuet +tuttuest +" to "+ tuttueet + "\n";
		}
		if(tutwedst != "")
		{
			swedc = swedc+ courseName+"(TUT)"+ "\n";
			swedt = swedt +tutwedst +" to "+ tutwedet + "\n";
		}
		if(tutthust != "")
		{
			sthuc = sthuc+ courseName+"(TUT)"+ "\n";
			sthut = sthut +tutthust +" to "+ tutthuet + "\n";
		}
		if(tutfrist != "")
		{
			sfric = sfric+ courseName+"(TUT)"+ "\n";
			sfrit = sfrit +tutfrist +" to "+ tutfriet + "\n";
		}
		
		
		if(LABdays.contains("M"))
		{
			smonc = smonc+ courseName+"(LAB)"+ "\n";
			smont = smont +labmonst +" to "+ labmonet + "\n";
		}
		if(LABdays.contains("T "))
		{
			stuec = stuec+ courseName+"(LAB)"+ "\n";
			stuet = stuet +labtuest +" to "+ labtueet + "\n";
		}
		if(LABdays.contains("W"))
		{
			swedc = swedc+ courseName+"(LAB)"+ "\n";
			swedt = swedt +labwedst +" to "+ labwedet + "\n";
		}
		if(LABdays.contains("Th"))
		{
			sthuc = sthuc+ courseName+"(LAB)"+ "\n";
			sthut = sthut +labthust +" to "+ labthuet + "\n";
		}
		if(LABdays.contains("F"))
		{
			sfric = sfric+ courseName+"(LAB)"+ "\n";
			sfrit = sfrit +labfrist +" to "+ labfriet + "\n";
		}
		lecmonst = ""; lecmonet = ""; lectuest = ""; lectueet = "";offhradd="";
				lecwedst = ""; lecwedet = ""; lecthust = ""; lecthuet = "";
				lecfrist = ""; lecfriet = ""; lecsatst = ""; lecsatet = "";
				lecsunst = ""; lecsunet = ""; tutmonst = ""; tutmonet = "";
				tuttuest = ""; tuttueet = ""; tutwedst = ""; tutwedet = "";
				tutthust = ""; tutthuet = ""; tutfrist = ""; tutfriet = "";
				tutsatst = ""; tutsatet = ""; tutsunst = ""; tutsunet = "";
				labmonst = ""; labmonet = ""; labtuest = ""; labtueet = "";
				labwedst = ""; labwedet = ""; labthust = ""; labthuet = "";
				labfrist = ""; labfriet = "";
	}
}	