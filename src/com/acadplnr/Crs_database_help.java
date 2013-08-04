package com.acadplnr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Crs_database_help {

	public static final String KEY_ROW_ID = "_id";
	public static final String KEY_COURSE_NAME = "Course_name";
	public static final String KEY_INSTRUCTOR = "Instructor";
	public static final String KEY_WEBLINK = "Weblink";
	//public static final String KEY_LECT_DAY= "Lecture_dayS";
	//public static final String KEY_LECT_STIME= "Lecture_Stime";
	//public static final String KEY_LECT_ETIME= "Lecture_Etime";
	//public static final String KEY_TUT_STIME= "Tut_Stime";
	//public static final String KEY_TUT_ETIME= "Tut_Etime";
	//public static final String KEY_TUT_DAY = "Tut_days";
	//public static final String KEY_LAB_STIME= "Lab_Stime";
	//public static final String KEY_LAB_ETIME= "Lab_Etime";
	public static final String KEY_LAB_DAY = "Lab_days";
	public static final String KEY_LAB_VENUE = "Lab_Venue";
	public static final String KEY_LEC_VENUE = "Lec_Venue";
	public static final String KEY_TUT_VENUE = "Tut_Venue";
	public static final String KEY_OFFICE_HR_ADD = "Office_hr_addr";
	//public static final String KEY_WEB_DETAIL = "htmldata";
	public static final String KEY_INSTRUCTOR_ID = "email";
	public static final String KEY_SCHEDULE = "schedule";
	public static final String KEY_CREDITS = "credits";
	
	private static final String DATABASE_TABLE = "Courses";
	private static final String DATABASE_NAME = "Course_contents";
	private static final int DATABASE_VERSION = 1;
	
	private dbHelper ourhelper;
	private Context ourcontext;
	private SQLiteDatabase course_database;

	private static class dbHelper extends SQLiteOpenHelper {

		

		

		public dbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROW_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ KEY_COURSE_NAME+ " TEXT, "
					+ KEY_INSTRUCTOR + " TEXT, "
					+ KEY_INSTRUCTOR_ID + " TEXT, "
					+ KEY_WEBLINK + " TEXT, "
					+ KEY_OFFICE_HR_ADD + " TEXT, "
					//+ KEY_LECT_DAY+ " TEXT, "
					//+ KEY_LECT_STIME + " TEXT, "
					//+ KEY_LECT_ETIME + " TEXT, "
					  + KEY_LAB_DAY+ " TEXT, "
					//+ KEY_LAB_STIME + " TEXT, "
					//+ KEY_LAB_ETIME + " TEXT, "
					//+ KEY_TUT_DAY+ " TEXT, "
					//+ KEY_TUT_STIME + " TEXT, "
					//+ KEY_TUT_ETIME + " TEXT, "
					+ KEY_LEC_VENUE + " TEXT, "
					+ KEY_LAB_VENUE + " TEXT, "
					+ KEY_TUT_VENUE + " TEXT, "
					+ KEY_SCHEDULE + " TEXT, "
					+ KEY_CREDITS + " TEXT);");
			

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	public Crs_database_help(Context c) {
		ourcontext = c;
	}

	public Crs_database_help open() throws SQLException {
		ourhelper = new dbHelper(ourcontext);
		course_database = ourhelper.getWritableDatabase();
		return this;
	}

	public void close() {
		ourhelper.close();
	}
// creates an entry in database
	public long createEntry ( String cname, String insname, String welink, String offhradd,
			String lec_v,String lab_v,String tut_v,String lab_d,String email, String schedule,String credits)throws Exception {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_COURSE_NAME , cname);
		cv.put(KEY_INSTRUCTOR , insname);
		cv.put(KEY_INSTRUCTOR_ID , email);
		cv.put(KEY_WEBLINK , welink);
		cv.put(KEY_OFFICE_HR_ADD , offhradd);
		cv.put(KEY_LAB_DAY , lab_d);
		/*cv.put(KEY_LAB_STIME , lab_St);
		cv.put(KEY_LAB_ETIME , lab_Et);
		cv.put(KEY_LECT_DAY , lect_d);
		cv.put(KEY_LECT_STIME , lect_St);
		cv.put(KEY_LECT_ETIME , lect_Et);
		cv.put(KEY_TUT_DAY , tut_d);
		cv.put(KEY_TUT_STIME , tut_St);
		cv.put(KEY_TUT_ETIME , tut_Et);*/
		cv.put(KEY_LEC_VENUE , lec_v);
		cv.put(KEY_LAB_VENUE , lab_v);
		cv.put(KEY_TUT_VENUE , tut_v);
		cv.put(KEY_SCHEDULE , schedule);
		cv.put(KEY_CREDITS , credits);
	//	cv.put(KEY_WEB_DETAIL , hdata);
		
		return course_database.insertOrThrow(DATABASE_TABLE, null, cv);
	}
	
	
// returns all data in a string seperated by -
	public String getdata() {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_COURSE_NAME,KEY_INSTRUCTOR,KEY_INSTRUCTOR_ID,KEY_WEBLINK,KEY_OFFICE_HR_ADD,KEY_LAB_DAY,
				//KEY_LECT_DAY,KEY_LECT_STIME,KEY_LECT_ETIME,KEY_LAB_DAY,KEY_LAB_STIME,
				//KEY_LAB_ETIME,KEY_TUT_DAY,KEY_TUT_STIME,KEY_TUT_ETIME,
				KEY_LEC_VENUE,KEY_LAB_VENUE,KEY_TUT_VENUE/*KEY_WEB_DETAIL*/,KEY_SCHEDULE,KEY_CREDITS}; 
		Cursor c = course_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		
		String result = "";
		
		for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
		{
			for(int i=0;i<columns.length;i++)
			{
			result = result + c.getString(i)+ "\t";
					
			}
			result = result + "\n";
		}
		return result;
	}

	public String GetCourseDetailById(long l) {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_COURSE_NAME,KEY_INSTRUCTOR,KEY_INSTRUCTOR_ID,KEY_WEBLINK,KEY_OFFICE_HR_ADD,KEY_LAB_DAY,
				//KEY_LECT_DAY,KEY_LECT_STIME,KEY_LECT_ETIME,KEY_LAB_DAY,KEY_LAB_STIME,
				//KEY_LAB_ETIME,KEY_TUT_DAY,KEY_TUT_STIME,KEY_TUT_ETIME,
				KEY_LEC_VENUE,KEY_LAB_VENUE,KEY_TUT_VENUE/*,KEY_WEB_DETAIL*/,KEY_SCHEDULE,KEY_CREDITS}; 
		Cursor c = course_database.query(DATABASE_TABLE, columns, KEY_ROW_ID + "=" + l, null, null, null, null);
		if(c!=null && c.moveToFirst())
		{
			
			String name="";
			for(int i=1;i<columns.length;i++)
			{
				name = name + c.getString(i) + "---"  ;
			}
			return name;
		}
		return null;
	}
	public String GetCourseNameById(long l) {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_COURSE_NAME,KEY_INSTRUCTOR,KEY_INSTRUCTOR_ID,KEY_WEBLINK,KEY_OFFICE_HR_ADD,KEY_LAB_DAY,
				//KEY_LECT_DAY,KEY_LECT_STIME,KEY_LECT_ETIME,KEY_LAB_DAY,KEY_LAB_STIME,
				//KEY_LAB_ETIME,KEY_TUT_DAY,KEY_TUT_STIME,KEY_TUT_ETIME,
				KEY_LEC_VENUE,KEY_LAB_VENUE,KEY_TUT_VENUE/*,KEY_WEB_DETAIL*/,KEY_SCHEDULE,KEY_CREDITS}; 
		Cursor c = course_database.query(DATABASE_TABLE, columns, KEY_ROW_ID + "=" + l, null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String name = c.getString(1);
			return name;
		}
		return null;
	}
	public String GetAllCourses(){
		String[] columns ={KEY_ROW_ID,KEY_COURSE_NAME,KEY_INSTRUCTOR,KEY_INSTRUCTOR_ID,KEY_WEBLINK,KEY_OFFICE_HR_ADD,KEY_LAB_DAY,
				/*KEY_LECT_DAY,KEY_LECT_STIME,KEY_LECT_ETIME,KEY_LAB_DAY,KEY_LAB_STIME,
				KEY_LAB_ETIME,KEY_TUT_DAY,KEY_TUT_STIME,KEY_TUT_ETIME,*/
				KEY_LEC_VENUE,KEY_LAB_VENUE,KEY_TUT_VENUE,/*KEY_WEB_DETAIL*/ KEY_SCHEDULE,KEY_CREDITS}; 
		Cursor c = course_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
		
		int iName = c.getColumnIndex(KEY_COURSE_NAME);
		String result = "";
		if (c.getCount()>0)
		{
			
			for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
			{
				result = result + c.getString(iName)+ "---" ;
			
			}
			return result; 
		}
		return null;
		
	}

	public void UpdateCourse(long l, String insname, String welink, String offhradd,
			String lab_d,String lec_v,String lab_v,String tut_v,String email,String credits) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_INSTRUCTOR , insname);
		cv.put(KEY_WEBLINK , welink);
		cv.put(KEY_LAB_DAY , lab_d);
		cv.put(KEY_OFFICE_HR_ADD , offhradd);
		cv.put(KEY_LEC_VENUE , lec_v);
		cv.put(KEY_LAB_VENUE , lab_v);
		cv.put(KEY_TUT_VENUE , tut_v);
		cv.put(KEY_INSTRUCTOR_ID , email);
		cv.put(KEY_CREDITS , credits);
		
		course_database.update(DATABASE_TABLE, cv,KEY_ROW_ID +"="+ l, null);
		return;
		
	}
	public void KeyRowIdUpdate(){
		ContentValues cv = new ContentValues();
		String[] columns ={KEY_ROW_ID,KEY_COURSE_NAME,KEY_INSTRUCTOR,KEY_INSTRUCTOR_ID,KEY_WEBLINK,KEY_OFFICE_HR_ADD,KEY_LAB_DAY,
				//KEY_LECT_DAY,KEY_LECT_STIME,KEY_LECT_ETIME,KEY_LAB_DAY,KEY_LAB_STIME,
				//KEY_LAB_ETIME,KEY_TUT_DAY,KEY_TUT_STIME,KEY_TUT_ETIME,
				KEY_LEC_VENUE,KEY_LAB_VENUE,KEY_TUT_VENUE,/*KEY_WEB_DETAIL*/ KEY_SCHEDULE,KEY_CREDITS}; 
		
		Cursor c = course_database.query(DATABASE_TABLE, columns,null, null, null, null, null);
		int m=1;
		for(c.moveToFirst();!(c.isAfterLast());c.moveToNext())
		{
			cv.put(KEY_ROW_ID, m);
			course_database.update(DATABASE_TABLE, cv, KEY_ROW_ID +"="+ c.getString(0), null);
			m++;
		}
		
		
	}

	public void deleteEntry(int m) {
		// TODO Auto-generated method stub
		
		course_database.delete(DATABASE_TABLE, KEY_ROW_ID + "="+m, null);
		KeyRowIdUpdate();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		String[] columns ={KEY_ROW_ID,KEY_COURSE_NAME,KEY_INSTRUCTOR,KEY_INSTRUCTOR_ID,KEY_WEBLINK,KEY_OFFICE_HR_ADD,KEY_LAB_DAY,
			//	KEY_LECT_DAY,KEY_LECT_STIME,KEY_LECT_ETIME,KEY_LAB_DAY,KEY_LAB_STIME,
			//	KEY_LAB_ETIME,KEY_TUT_DAY,KEY_TUT_STIME,KEY_TUT_ETIME,
				KEY_LEC_VENUE,KEY_LAB_VENUE,KEY_TUT_VENUE,/*KEY_WEB_DETAIL*/ KEY_SCHEDULE,KEY_CREDITS}; 
		Cursor c = course_database.query(DATABASE_TABLE, columns, null, null, null, null, null);
		int i = c.getCount();
		
		return i;
	}
}