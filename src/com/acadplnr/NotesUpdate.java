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

public class NotesUpdate extends Activity implements OnClickListener {
     EditText n_title,n_body;
     String ClickedPosition;
     Button bok;
     Global1 GLOBE;
     @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_add_notes);
		
		n_title = (EditText) findViewById(R.id.nt);
		n_body = (EditText) findViewById(R.id.nb);
		bok = (Button) findViewById(R.id.bokedit);
		bok.setOnClickListener(this);
		
		Bundle gotBasket = getIntent().getExtras();
		ClickedPosition = gotBasket.getString("clkpos");
		
		Notes_database info = new Notes_database(this);

		info.open();
		String RowId = ClickedPosition;
		long l = Long.parseLong(RowId);
		String c = info.GetNotesDetailById(l);

		if (c != null) {

			String[] ndata;
			String delimiter = "-";
			ndata = c.split(delimiter);

			n_title.setText(ndata[0]);
			n_body.setText(ndata[1]);
		
	}
		info.close();
  }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		boolean didItWORK = true;
		
		try {
			String ntitle = n_title.getText().toString();
			String nbody = n_body.getText().toString();
			
			Notes_database entry = new Notes_database(NotesUpdate.this);
			entry.open();
			long l = Long.parseLong(ClickedPosition);
			entry.UpdateNotes(l, ntitle, nbody);
			entry.close();
		} catch (Exception e) {
			didItWORK = false;
			String error = e.toString();
			Dialog h = new Dialog(this);
			h.setTitle(" :(");
			TextView tv1 = new TextView(this);
			tv1.setText(error);
			h.setContentView(tv1);
			h.show();
		} finally {
			if (didItWORK) {
				Dialog d = new Dialog(this);
				d.setTitle("YEAH !");
				TextView tv = new TextView(this);
				tv.setText("Success!");
				d.setContentView(tv);
				d.show();
				Global1.tabN=1;
				Intent i = new Intent(NotesUpdate.this,MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		}
		
		
	}
	
     
}
