package com.acadplnr;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Notes_database {
       
	public static final String KEY_ROW_ID = "_id";
	public static final String KEY_NOTES_TITLE="Title";
	public static final String KEY_NOTES_BODY="Body";
	
	private static final String DATABASE_TABLE = "Notes";
	private static final String DATABASE_NAME = "Notes_contents";
	private static final int DATABASE_VERSION = 1;
	
	private dbHelper myhelper;
	private Context mycontext;
	private SQLiteDatabase notes_database;
	
	private static class dbHelper extends SQLiteOpenHelper{

		public dbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROW_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_NOTES_TITLE+ " TEXT, "
					+ KEY_NOTES_BODY + " TEXT);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	public Notes_database(Context d) {
		mycontext = d;
	}
	public Notes_database open() throws SQLException {
		myhelper = new dbHelper(mycontext);
		notes_database = myhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		myhelper.close();
	}
	// creates an entry in database
		public long createEntry ( String ntitle, String nbody)throws Exception {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_NOTES_TITLE ,ntitle);	
			cv.put(KEY_NOTES_BODY ,nbody);
			return notes_database.insertOrThrow(DATABASE_TABLE, null, cv);
		}
		
		public String GetNotesDetailById(long l) {
			// TODO Auto-generated method stub
			String[] columns ={KEY_ROW_ID,KEY_NOTES_TITLE,KEY_NOTES_BODY}; 
			Cursor c = notes_database.query(DATABASE_TABLE, columns, KEY_ROW_ID + "=" + l, null, null, null, null);
			if(c!=null && c.moveToFirst())
			{
				
				String name="";
				for(int i=1;i<columns.length;i++)
				{
					name = name + c.getString(i) + "-"  ;
				}
				return name;
			}
			return null;
		}
		public String GetAllNotes(){
			String[] columns ={KEY_ROW_ID,KEY_NOTES_TITLE,KEY_NOTES_BODY};
			Cursor c = notes_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
			
			int iName = c.getColumnIndex(KEY_NOTES_TITLE);
			String result = "";
			if (c.getCount()>0)
			{
				
				for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
				{
					result = result + c.getString(iName)+ "-" ;
				
				}
				return result; 
			}
			return null;
		}
		public void UpdateNotes(long l, String ntitle, String nbody) {
			// TODO Auto-generated method stub
			ContentValues cv = new ContentValues();
			cv.put(KEY_NOTES_TITLE , ntitle);
			cv.put(KEY_NOTES_BODY , nbody);	
			
			notes_database.update(DATABASE_TABLE, cv,KEY_ROW_ID +"="+ l, null);
			return;
			
		}
		public void KeyRowIdUpdate(){
			ContentValues cv = new ContentValues();
			String[] columns ={KEY_ROW_ID,KEY_NOTES_TITLE,KEY_NOTES_BODY}; 
			
			Cursor c = notes_database.query(DATABASE_TABLE, columns,null, null, null, null, null);
			int m=1;
			for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
			{
				cv.put(KEY_ROW_ID, m);
				notes_database.update(DATABASE_TABLE, cv, KEY_ROW_ID +"="+ c.getString(0), null);
				m++;
			}
			
			
		}

		public void deleteEntry(int m) {
			// TODO Auto-generated method stub
			
			notes_database.delete(DATABASE_TABLE, KEY_ROW_ID + "="+m, null);
			KeyRowIdUpdate();
			
			
			
			
		}

		public int getCount() {
			// TODO Auto-generated method stub
			String[] columns ={KEY_ROW_ID,KEY_NOTES_TITLE,KEY_NOTES_BODY}; 
			Cursor c = notes_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
			int i = c.getCount();
			
			return i;
		}
}
