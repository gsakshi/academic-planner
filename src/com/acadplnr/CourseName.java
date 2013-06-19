package com.acadplnr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import com.acadplnr.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class CourseName extends Activity implements OnClickListener {
	
	private String COURSE;
	private static final String[] COURSES = new String[] {
		
		"AE201","AE201A","AE232","AE311","AE311A","AE321","AE321A","AE331","AE331A",
		"AE341","AE411","AE461","AE471","AE601","AE601A","AE602","AE602A","AE603","AE605",
		"AE605A","AE610","AE610A","AE618","AE618A","AE647","AE647A","AE652","AE652A",
		"AE670","AE670A","AE672","AE672A","AE684","AE684A","AE686","AE686A","AE688",
		"AE688A","AE698B","AE699","AE753","AE753A","AE799","ART105","ART105A","ART411",
		"ART411A","BSE211A","BSE212","BSE292","BSE301A","BSE311A","BSE312A","BSE411A",
		"BSE412A","BSE491","BSE492","BSE492A","BSE498","BSE613","BSE615","BSE629",
		"BSE629A","BSE630","BSE630A","BSE632","BSE640","BSE671","BSE681","BSE699","BSE701",
		"BSE702","BSE799","CE211A","CE242","CE311","CE321","CE321A","CE331","CE331A",
		"CE341A","CE351A","CE361","CE361A","CE371","CE371A","CE432","CE451","CE491",
		"CE601","CE602","CE610","CE611N","CE616","CE620","CE621N","CE623","CE631N",
		"CE638","CE639","CE640","CE642","CE649","CE671N","CE677N","CE683","CE689N","CE699",
		"CE799","CHE251A","CHE300A","CHE312A","CHE313A","CHE352A","CHE453","CHE492",
		"CHE494","CHE495","CHE601","CHE611","CHE618","CHE621","CHE631","CHE633","CHE641",
		"CHE661","CHE678","CHE679","CHE684","CHE684A","CHE699","CHE701","CHE799","CHM101A",
		" CHM101N","CHM102A","CHM201N","CHM203A","CHM301","CHM303A"," CHM305A"," CHM321A",
		"CHM345A","CHM361A","CHM391A","CHM401","CHM404","CHM421","CHM423","CHM441","CHM503",
		"CHM521","CHM600","CHM602","CHM609","CHM611","CHM616","CHM621","CHM629","CHM636",
		"CHM646","CHM649","CHM650","CHM651","CHM664","CHM685","CHM698","CHM700","CHM7990",
		"CHM800","CHM801","COM200","CS210","CS210A","CS251A","CS330","CS330A","CS340",
		"CS340A","CS350","CS350A","CS397","CS425","CS425A","CS433","CS433A","CS455",
		"CS455A","CS497","CS498","CS499","CS601","CS602","CS641","CS641A","CS648","CS648A",
		"CS653","CS671","CS671A","CS677","CS687","CS688","CS697","CS698Q","CS698T",
		"CS698X","CS699","CS699.","CS738","CS771","CS799","DES601","DES602","DES621",
		"DES633","DES635","DES681","DES699","DES799","ECO101","ECO101A"," ECO201A",
		"ECO301A","ECO341A","ECO402","ECO405","ECO413","ECO422","ECO434","ECO502","ECO502A",
		"ECO535","ECO598","ECO732","ECO738","ECO738A","ECO751","ECO799","EE200","EE200A",
		"EE320","EE320A","EE330","EE330A","EE370","EE370A","EE380","EE380A","EE390A",
		"EE393A","EE491","EE601","EE601A","EE602","EE602A","EE604","EE604A","EE605",
		"EE605A","EE610","EE610A","EE612","EE612A","EE614","EE614A","EE616","EE616A",
		"EE618","EE618A","EE621","EE621A","EE624","EE624A","EE627","EE627A","EE630",
		"EE630A","EE634","EE634A","EE641","EE641A","EE645","EE645A","EE646","EE646A",
		"EE647","EE647A","EE650","EE650A","EE654","EE654A","EE658","EE658A","EE660",
		"EE660A","EE664","EE664A","EE673","EE673A","EE680","EE680A","EE698C","EE698J",
		"EE698K","EE699","EE799","EEM602","EEM603","EEM606","EEM613","EEM613A","EEM699",
		"ENG434","ENG434A","ENG440","ENG440A","ENG703","ENG712","ENG719","ENG733","ENG749",
		"ENG754","ENG799","ESC101A","ESC101N","ESC201A","ESO201A","ESO202","ESO202A","ESO203A",
		"ESO204","ESO204A","ESO205A","ESO206A","ESO207A","ESO208A","ESO209A","ESO210",
		"ESO211","ESO212","ESO214","ESO218","ESO219","HSS702","IME602","IME602A","IME603",
		"IME603A","IME605","IME605A","IME636","IME636A","IME692","IME692A","IME697",
		"IME698","IME699","IME799","LIF101A","LT601","LT631","LT699","LT799","MBA601",
		"MBA601A","MBA606","MBA606A","MBA611","MBA611A","MBA617","MBA617A","MBA622",
		"MBA622A","MBA623","MBA623A","MBA631","MBA631A","MBA634","MBA637","MBA637A",
		"MBA641"," MBA641A","MBA643","MBA643A","MBA646"," MBA646A","MBA651","MBA651A",
		"MBA654","MBA654A","MBA664","MBA664A","MBA675","MBA675A","MBA676","MBA676A",
		"MBA679","MBA679A","MBA697","MBA698","MBA699","ME251A","ME300","ME301","ME301A",
		"ME321","ME321A","ME352","ME352A","ME361","ME361A","ME399A","ME401","ME451",
		"ME453","ME471N","ME621","ME621A","ME623","ME623A","ME625","ME625A","ME630",
		"ME630A","ME631","ME631A","ME641","ME641A","ME661","ME661A","ME662","ME662A",
		"ME667","ME667A","ME681","ME681A","ME685","ME685A","ME690","ME690A","ME699",
		"ME727","ME727A","ME757","ME757A","ME761","ME761A","ME763","ME763A","ME766",
		"ME766A","ME781","ME781A","ME799","MME799","MS601","MS602","MS603","MS604",
		"MS698","MS699","MS799","MSE200","MSE201A","MSE300A"," MSE301A","MSE302A",
		"MSE303A","MSE310","MSE311A","MSE313A","MSE349A","MSE410","MSE415","MSE470","MSE498",
		"MSE499","MSE615","MSE615A","MSE626A","MSE626N","MSE631","MSE631A","MSE634",
		"MSE634A","MSE642","MSE642A","MSE659","MSE659A","MSE664","MSE664A","MSE682",
		"MSE682A","MSE690","MSE691","MSE693A","MSE693N","MSE694","MSE694A","MSE699",
		"MSE799","MSO202A","MSO203B","MTH101A","MTH102A","MTH201","MTH201A","MTH204",
		"MTH204A","MTH301","MTH301A","MTH305A","MTH311","MTH311A","MTH403","MTH403A",
		"MTH405","MTH405A","MTH409","MTH409A","MTH415","MTH415A","MTH417","MTH417A",
		"MTH421","MTH421A","MTH423","MTH423A","MTH428","MTH512","MTH513"," MTH513A",
		"MTH515","MTH515A","MTH517","MTH517A","MTH598","MTH612","MTH612A","MTH624",
		"MTH624A","MTH649","MTH649A","MTH686","MTH686A","MTH691","MTH692","MTH701",
		"MTH701A","MTH753","MTH753A","MTH754","MTH754A","MTH799","NT602","NT611","NT611A",
		"NT699","NT799","PE101A","PHI141","PHI141A","PHI455","PHI455A","PHI752","PHI757",
		"PHI768","PHI799","PHY100","PHY101A","PHY101N","PHY102A","PHY102N","PHY103A",
		"PHY103N","PHY224","PHY224A","PHY301","PHY301A","PHY303","PHY303A","PHY315",
		"PHY315A","PHY400","PHY401","PHY401A","PHY407","PHY421","PHY421A","PHY431",
		"PHY431A","PHY441","PHY461","PHY462","PHY500","PHY501","PHY502","PHY543",
		"PHY552","PHY563","PHY565","PHY566","PHY568","PHY590","PHY599"," PHY601",
		"PHY603","PHY615","PHY632","PHY647","PHY659","PHY679","PHY681","PHY682","PHY690F",
		"PHY690G","PHY799","PSY151A","PSY451","PSY451A","PSY458","PSY458A","PSY468",
		"PSY468A","PSY774","PSY775","PSY783","PSY798","PSY799","SE308","SE309","SE314",
		"SE315","SE332","SE381","SOC171","SOC171B","SOC470","SOC470A","SOC486","SOC486A",
		"SOC724","SOC745","SOC746","SOC750","SOC799","TA101A","TA101N","TA201A","TA201N",
		"TA202A","TA202N",
		"AE211","AE211A","AE251A","AE312","AE322","AE332","AE342","AE422","AE462","AE472",
		"AE481","AE604","AE612","AE614","AE625","AE641","AE648","AE658","AE662","AE675",
		"AE678","AE681","AE683","AE687","AE696","AE698","AE699","AE699.","AE701","AE799",
		"ART103","ART103A","ART105","ART105A","ART106A","ART402","BSE216","BSE221A","BSE222A",
		"BSE223A","BSE314","BSE341","BSE391","BSE392","BSE499","BSE633","BSE634","BSE636",
		"BSE638","BSE652","BSE653","BSE671","BSE680","BSE681","BSE699","BSE701","BSE702",
		"BSE799","CE222","CE242A","CE251","CE262A","CE272A","CE312N","CE322","CE332","CE362",
		"CE373","CE382","CE454","CE462","CE491","CE492","CE613","CE615","CE619","CE622N",
		"CE629","CE632N","CE634N","CE636","CE641","CE645","CE646","CE648","CE673N","CE674",
		"CE676","CE682N","CE690","CE699","CE699.","CE723N","CE799","CHE100","CHE211A","CHE221",
		"CHE221A","CHE261A","CHE313","CHE361","CHE362","CHE381","CHE391","CHE452","CHE453",
		"CHE463","CHE494","CHE495","CHE496","CHE601","CHE602","CHE613","CHE642","CHE659",
		"CHE662","CHE674","CHE688","CHE699","CHE699.","CHE702","CHE799","CHE802","CHM101A",
		"CHM101N","CHM102A","CHM201N","CHM201R","CHM202A","CHM222","CHM222A","CHM242A"
		,"CHM302","CHM341","CHM343","CHM402","CHM422","CHM442","CHM443","CHM481","CHM482",
		"CHM602","CHM612","CHM616","CHM631","CHM662","CHM684","CHM695","CHM700","CHM700.",
		"CHM799","CHM800","CHM801","COM200","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
		,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
		,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
		,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
		,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
		,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
		,"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
		
	};
	Button b;
	TextView sethtml;
	String html, coursetitle = "", prof = "", schedule = "", profname[] = {},
			profname1 = "", profname2 = "", profemail = "", lecv = "",
			tutv = "", labv = "", lecdays = "", tutdays = "", labdays = "",
			lecmonst = "", lecmonet = "", lectuest = "", lectueet = "",
			lecwedst = "", lecwedet = "", lecthust = "", lecthuet = "",
			lecfrist = "", lecfriet = "", lecsatst = "", lecsatet = "",
			lecsunst = "", lecsunet = "", tutmonst = "", tutmonet = "",
			tuttuest = "", tuttueet = "", tutwedst = "", tutwedet = "",
			tutthust = "", tutthuet = "", tutfrist = "", tutfriet = "",
			tutsatst = "", tutsatet = "", tutsunst = "", tutsunet = "",
			labmonst = "", labmonet = "", labtuest = "", labtueet = "",
			labwedst = "", labwedet = "", labthust = "", labthuet = "",
			labfrist = "", labfriet = "", labsatst = "", labsatet = "",
			labsunst = "", labsunet = "";
		AutoCompleteTextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coursename);
		textView = (AutoCompleteTextView) findViewById(R.id.actv);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COURSES);
		textView.setAdapter(adapter);
		b = (Button) findViewById(R.id.button1);
		sethtml = (TextView)findViewById(R.id.TV1);
		b.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.button1:
			COURSE = textView.getText().toString();
			/*HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://172.26.142.66:6060/Utils/CourseInfoPopup2.asp?Course="+COURSE);
			HttpResponse response = null;
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			InputStream in = null;
			try {
				in = response.getEntity().getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder str = new StringBuilder();
			String line = "";
			try {
				while((line = reader.readLine()) != null)
				{
				    str.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			html = str.toString();
			*/
			parsehtml(html);
		}
		
	}
	
	public void parsehtml(String html) {

		Document doc = null;
		try {
			doc = Jsoup.connect("http://172.26.142.66:6060/Utils/CourseInfoPopup2.asp?Course="+COURSE).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Document doc = Jsoup.parse(html);

		Elements tableRows = doc.select("tr");

		coursetitle = tableRows.get(1).select("td").get(1).text();
		prof = tableRows.get(2).select("td").get(1).text();
		
		profname = prof.split(", ");
		for (int i = 0; i < profname.length; i++) {
			if (i == 0)
				profname1 = profname[i];

			else if (i == 1)
				profname2 = profname[i];
		}
		sethtml.setText(profname1+" * "+profname2);
		profemail = tableRows.get(4).select("td").get(1).text();
		schedule = tableRows.get(7).select("td").get(1).text();
		breakschedule();
	}

	public void breakschedule() {

		int lecindex, tutindex, labindex;
		lecindex = schedule.indexOf("LEC");
		tutindex = schedule.indexOf("TUT");
		labindex = schedule.indexOf("LAB");
		int flag = 0,j=0;
		if (lecindex != -1) {

			String lecd = "", lect = "", lecvenue = "";
			
			if(flag==0){
				 j = lecindex + 5;
				 flag=1;
			}
			
			while (((tutindex!=-1 && j < tutindex) || tutindex==-1) && ((labindex!=-1 && j < labindex) || labindex==-1) && j<schedule.length() ) {
				lecd = ""; lect = ""; lecvenue = "";
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
					lecd = lecd + schedule.charAt(j);
					j++;
				}
				j++;
				if (schedule.charAt(j) >= '0' && schedule.charAt(j) <= '9') {
					while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
						lect = lect + schedule.charAt(j);
						j++;
					}
				}
				j++;
				while (schedule.charAt(j) != ';' && j<schedule.length() ) {
					lecvenue = lecvenue + schedule.charAt(j);
					j++;
				}
				j++;
				if (lecvenue.contentEquals("REQ")) {
					if (lecv.contentEquals("null"))
						lecv = "Not Yet Set";
				} else
					lecv = lecvenue;
				
				if (lecd.contains("M")) {
					lecdays = lecdays + "M ";
					lecmonst = lect.split("-")[0];
					lecmonet = lect.split("-")[1];
				}
				if (lecd.contains("W")) {
					lecdays = lecdays + "W ";
					lecwedst = lect.split("-")[0];
					lecwedet = lect.split("-")[1];
				}
				if (lecd.contains("F")) {
					lecdays = lecdays + "F ";
					lecfrist = lect.split("-")[0];
					lecfriet = lect.split("-")[1];
				}
				if (lecd.contains("S")) {
					lecdays = lecdays + "S ";
					lecsatst = lect.split("-")[0];
					lecsatet = lect.split("-")[1];
				}
				if (lecd.contains("Th")) {
					lecdays = lecdays + "Th ";
					lecthust = lect.split("-")[0];
					lecthuet = lect.split("-")[1];
				}
				if (lecd.contains("T")
						&& lecd.charAt(lecd.indexOf("T") + 1) != 'h') {
					lecdays = lecdays + "T ";
					lectuest = lect.split("-")[0];
					lectueet = lect.split("-")[1];
				}
			}
		}
		flag=0; j=0;
		if (tutindex != -1) {

			String tutd = "", tutt = "", tutvenue = "";
			if(flag==0){
				j = tutindex + 5;
				flag=1;
			}
			while ((labindex!=-1 && j < labindex) || labindex==-1 && j<schedule.length() ) {
				tutd = ""; tutt = ""; tutvenue = "";
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
					tutd = tutd + schedule.charAt(j);
					j++;
				}
				j++;
				if (schedule.charAt(j) >= '0' && schedule.charAt(j) <= '9') {
					while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
						tutt = tutt + schedule.charAt(j);
						j++;
					}
				}
				j++;
				while (schedule.charAt(j) != ';' && j<schedule.length() ) {
					tutvenue = tutvenue + schedule.charAt(j);
					j++;
				}
				j++;
				if (tutvenue.contentEquals("REQ")) {
					if (tutv.contentEquals("null"))
						tutv = "Not Yet Set";
				} else
					tutv = tutvenue;
				
				if (tutd.contains("M")) {
					tutdays = tutdays + "M ";
					tutmonst = tutt.split("-")[0];
					tutmonet = tutt.split("-")[1];
				}
				if (tutd.contains("W")) {
					tutdays = tutdays + "W ";
					tutwedst = tutt.split("-")[0];
					tutwedet = tutt.split("-")[1];
				}
				if (tutd.contains("F")) {
					tutdays = tutdays + "F ";
					tutfrist = tutt.split("-")[0];
					tutfriet = tutt.split("-")[1];
				}
				if (tutd.contains("S")) {
					tutdays = tutdays + "S ";
					tutsatst = tutt.split("-")[0];
					tutsatet = tutt.split("-")[1];
				}
				if (tutd.contains("Th")) {
					tutdays = tutdays + "Th ";
					tutthust = tutt.split("-")[0];
					tutthuet = tutt.split("-")[1];
				}
				/*if (tutd.contains("T")
						&& tutd.charAt(tutd.indexOf("T") + 1) != 'h' ) {
					tutdays = tutdays + "T ";
					tuttuest = tutt.split("-")[0];
					tuttueet = tutt.split("-")[1];
				}*/
			}
		}
		flag =0;j=0;
		if (labindex != -1) {

			String labd = "", labt = "", labvenue = "";
			if(flag==0){
				j = labindex + 5;
				flag=1;
			}
			while (j<schedule.length() ) {
				labd = ""; labt = ""; labvenue = "";
				while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
					labd = labd + schedule.charAt(j);
					j++;
				}
				j++;
				if (schedule.charAt(j) >= '0' && schedule.charAt(j) <= '9') {
					while (schedule.charAt(j) != ' ' && j<schedule.length() ) {
						labt = labt + schedule.charAt(j);
						j++;
					}
				}
				j++;
				while (schedule.charAt(j) != ';' && j<schedule.length() ) {
					labvenue = labvenue + schedule.charAt(j);
					j++;
				}
				j++;
				if (labvenue.contentEquals("REQ")) {
					if (labv.contentEquals("null"))
						labv = "Not Yet Set";
				} else
					labv = labvenue;
				
				if (labd.contains("M")) {
					labdays = labdays + "M ";
					labmonst = labt.split("-")[0];
					labmonet = labt.split("-")[1];
				}
				if (labd.contains("W")) {
					labdays = labdays + "W ";
					labwedst = labt.split("-")[0];
					labwedet = labt.split("-")[1];
				}
				if (labd.contains("F")) {
					labdays = labdays + "F ";
					labfrist = labt.split("-")[0];
					labfriet = labt.split("-")[1];
				}
				if (labd.contains("S")) {
					labdays = labdays + "S ";
					labsatst = labt.split("-")[0];
					labsatet = labt.split("-")[1];
				}
				if (labd.contains("Th")) {
					labdays = labdays + "Th ";
					labthust = labt.split("-")[0];
					labthuet = labt.split("-")[1];
				}
				if (labd.contains("T")
						&& labd.charAt(labd.indexOf("T") + 1) != 'h') {
					labdays = labdays + "T ";
					labtuest = labt.split("-")[0];
					labtueet = labt.split("-")[1];
				}
			}
		
		}
	}
}