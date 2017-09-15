package com.example.expensetracker;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AddExpense extends Activity implements OnItemSelectedListener,OnClickListener{

	private static final int NOTIFY_ME_ID=1337;
	private Timer timer=new Timer();
	private int count=0;
	Cursor c1;
	SQLiteDatabase db;

	TextView tv1;
	EditText name,amount;
	Button b1,b2;
	final Calendar dateAndTime=Calendar.getInstance();
	DateFormat fmtDateAndTime=DateFormat.getDateInstance();
	DatePickerDialog.OnDateSetListener d;
	//TimePickerDialog.OnTimeSetListener t;
	
	Spinner sp1;
	private static String url_all_categories = Connet.url+"category.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	private static String url_all_categories2 = Connet.url+"addExpense.php";
	private static String url_all_categories3 = Connet.url+"selectCategoryLimit.php";
	private static String url_all_categories4 = Connet.url+"categoryExpenseTotal.php";
	private static String url_all_categories5 = Connet.url+"selectUser.php";
	private static String url_all_categories6 = Connet.url+"expenseTotal.php";
	
	JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	private String TAG_sid="category_id";
	private String TAG_name="category_name";
	
	
	String keyword;
	String uid;
	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();

	public static final String TAG_stud = "category_data";
    String success="";
    ProgressDialog pDialog;
	String responseBody;
	
	List<String> lst=new ArrayList<String>();
	
	@SuppressLint("NewApi") @Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		
		amount=(EditText)findViewById(R.id.amt);
		   name=(EditText)findViewById(R.id.exp);
		
		b1=(Button)findViewById(R.id.sdate);
		b2=(Button)findViewById(R.id.add);
		tv1=(TextView)findViewById(R.id.date);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		sp1=(Spinner)findViewById(R.id.category);
		db=openOrCreateDatabase("expense_tracker.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);

			/*t=new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				dateAndTime.set(Calendar.HOUR_OF_DAY, arg1);
				dateAndTime.set(Calendar.MINUTE, arg2);
				updateLabel();
			}
		};*/
		
			d=new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				dateAndTime.set(Calendar.YEAR, arg1);
				dateAndTime.set(Calendar.MONTH,arg2);
				dateAndTime.set(Calendar.DAY_OF_MONTH, arg3);
				updateLabel();
			}
		};
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
	    new test().execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
		return true;
	}
	
	class test extends AsyncTask<String, String, String>
	{
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AddExpense.this);
			pDialog.setMessage("Wait..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {
			try {
			
				if(ConnectivityReceiver.isConnected())
				{

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				
	 			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
	 	
	 			Log.d("branch ", json.toString());
	 		
	  		zone = json.getJSONArray(TAG_stud);
	  		
	 for (int i = 0; i < zone.length(); i++)
	 {
		JSONObject c = zone.getJSONObject(i);

		String zoneId = c.getString(TAG_sid);
	 	String zoneName = c.getString(TAG_name);
	 		lst.add(zoneName);
	 	
		HashMap<String, String> map = new HashMap<String, String>();
		
		// adding each child node to HashMap key => value
		map.put(TAG_sid,zoneId);
		map.put(TAG_name, zoneName);

		// adding HashList to ArrayList
		categoryList.add(map);
		Log.d("All Data: "+i, categoryList.get(i).get(TAG_sid));

		
	 }
		
	}
				 else
				 {
					 c1=db.query("category",null,null,null,null,null,null);
					    startManagingCursor(c1);
				c1.moveToFirst();
				 for (int i = 0; i < c1.getCount(); i++)
				 {
					
					String zoneId = c1.getString(0);
				 	String zoneName = c1.getString(1);
		 		lst.add(zoneName);
				 	
					HashMap<String, String> map = new HashMap<String, String>();
					
					// adding each child node to HashMap key => value
					map.put(TAG_sid,zoneId);
					map.put(TAG_name, zoneName);

					// adding HashList to ArrayList
					categoryList.add(map);
					Log.d("Data: "+i, categoryList.get(i).get(TAG_sid));

				 c1.moveToNext();
				 }
				 
					 
				 }

	}
		catch (Exception e)
	{
			Log.d("ërror",e.getMessage());
	}
				

			
			return null;
			
		}
		
		
		protected void onPostExecute(String file_url)
		{
			// dismiss the dialog after getting all products
	try
	{
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
		 			/**
					 * Updating parsed JSON data into ListView
					 * */
					
					
			        sp1=(Spinner)findViewById(R.id.category);
//			        ed=(EditText)findViewById(R.id.editText1);
			    	
			        ArrayAdapter<String>  aap = new ArrayAdapter<String>(AddExpense.this,android.R.layout.simple_spinner_dropdown_item,lst);
		
//					setListAdapter(adapter);
			        sp1.setAdapter(aap);

			      		        
//					setListAdapter(adapter);
						}  });
			
		}
		catch (Exception e)
	{
			Log.d("ërror",e.getMessage());
	}
	
			}

	

}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId()==R.id.sdate)
		{
			
	//DatePickerDialog d1=new DatePickerDialog(DateDialogSampleActivity.this,	d,dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
	DatePickerDialog d1=new DatePickerDialog(AddExpense.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
	d1.show();
		}
		else if(arg0.getId()==R.id.add)
		{
			if(!validation())
			{
				if(checkLimit())
				{
					Toast.makeText(getApplicationContext(),"Category limit exceeded", 10000).show();
					TimerTask task=new TimerTask() {
						
						
						public void run() {
							// TODO Auto-generated method stub
							notifyMe();
						}
						
						private void notifyMe() {
							// TODO Auto-generated method stub
							int t=sp1.getSelectedItemPosition();
							HashMap<String, String> map = categoryList.get(t);
							
							final NotificationManager mgr=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
							Notification note=new Notification(R.drawable.ic_launcher,"Status Message!",System.currentTimeMillis());
							
							Intent it=new Intent(getApplicationContext(),Sample1.class);
							
							PendingIntent i=PendingIntent.getActivity(getApplicationContext(), 0, it,0);
									
							note.setLatestEventInfo(getApplicationContext(),"Limit Exceeded","You just crossed the limit of "+map.get(TAG_name).toString()+" expenses",i);
							
							note.number=++count;
							mgr.notify(NOTIFY_ME_ID,note);
							
							try { Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification); r.play(); } catch (Exception e) { e.printStackTrace(); }
						}

						
					};
					timer.schedule(task,1000);
				}
				
				if(checkOverallLimit())
				{
					Toast.makeText(getApplicationContext(),"Overall limit exceeded", 10000).show();
					TimerTask task=new TimerTask() {
						
						
						public void run() {
							// TODO Auto-generated method stub
							notifyMe();
						}

						private void notifyMe() {
							// TODO Auto-generated method stub
							
							final NotificationManager mgr=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
							Notification note=new Notification(R.drawable.ic_launcher,"Status Message!",System.currentTimeMillis());
							
							Intent it=new Intent(getApplicationContext(),Home.class);
							
							PendingIntent i=PendingIntent.getActivity(getApplicationContext(), 0, it,0);
									
							note.setLatestEventInfo(getApplicationContext(),"Limit Exceeded","You just crossed the overall limit of ",i);
							
							note.number=++count;
							mgr.notify(NOTIFY_ME_ID,note);
							
							try { Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification); r.play(); } catch (Exception e) { e.printStackTrace(); }
						}
					};
					timer.schedule(task,1000);
				}
					try
					{
						int t=sp1.getSelectedItemPosition();
						HashMap<String, String> map = categoryList.get(t);
						
						if(ConnectivityReceiver.isConnected())
						{

						//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
				
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("name",name.getText().toString()));
						params.add(new BasicNameValuePair("amount",amount.getText().toString()));
						params.add(new BasicNameValuePair("date",tv1.getText().toString()));
						params.add(new BasicNameValuePair("category",map.get(TAG_sid).toString()));
						params.add(new BasicNameValuePair("uid",uid));
						JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
						String success=json.getString("success");
			
						if(success.equals("1"))
						{
							Toast.makeText(getApplicationContext(),"Inserted", 10000).show();
						}
						}
						else
						{
							ContentValues cv=new ContentValues();
							cv.put("expense_name", name.getText().toString());
							cv.put("amount", amount.getText().toString());
							cv.put("date", tv1.getText().toString());
							cv.put("user_id", uid);
							cv.put("category_id",map.get(TAG_sid).toString());
							cv.put("status","1");
							
							db.insert("expense",null,cv);
							cv.clear();
							Toast.makeText(getApplicationContext(),"Offline inserted", 10000).show();
							db.close();
						}
					

					}
					catch (Exception e)
					{
						Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
				
						Log.d("ërror",e.getMessage());
						//e.printStackTrace();
					}
				
			}
		}
	}
		/*else
		{
			TimePickerDialog t1=new TimePickerDialog(AddExpense.this, t, dateAndTime.get(Calendar.HOUR_OF_DAY),dateAndTime.get(Calendar.MINUTE), true);
			t1.show();
		}*/

	
	private void updateLabel() {
		//tv1.setText(fmtDateAndTime.format(dateAndTime.getTime()));
		
		Date from=new Date();
		from=dateAndTime.getTime();
		String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
		//tv1.setText(fmtDateAndTime.format(dateAndTime.getTime()));
		tv1.setText(format);
		}
	
	
	private Boolean validation() {
		Boolean isvalid=false;
				Log.v("TAG","Validation");
				
				if(TextUtils.isEmpty(amount.getText().toString())){
					Log.v("TAG",amount.getText().toString());
					amount.setError("Enter Amount");
					isvalid=true;
					amount.requestFocus();
				}
				 else if (!amount.getText().toString().matches("[0-9]+")) {
					 amount.setError("Accept Numbers Only.");
					 isvalid=true;
					 amount.requestFocus();
				 }
				 else if(Integer.parseInt(amount.getText().toString())<=0)
				 {
					 amount.setError("Amount must be greater than zero.");
					 isvalid=true;
					 amount.requestFocus();
				 }
				return isvalid;
			}
	private Boolean checkLimit()
	{
		String from="",to="";
		int limit=0,total=0;
		try
		{
			if(ConnectivityReceiver.isConnected())
			{

		int t=sp1.getSelectedItemPosition();
		HashMap<String, String> map = categoryList.get(t);
	//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
			
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("name",name.getText().toString()));
		//params.add(new BasicNameValuePair("amount",amount.getText().toString()));
		//params.add(new BasicNameValuePair("date",tv1.getText().toString()));
		params.add(new BasicNameValuePair("category",map.get(TAG_sid).toString()));
		params.add(new BasicNameValuePair("uid",uid));
		JSONObject json = jParser.makeHttpRequest(url_all_categories3, "GET", params);
		String success=json.getString("success");
		from=json.getString("from");
		to=json.getString("to");
		limit=Integer.parseInt(json.getString("amount"));
		
		
		/*if(success.equals("1"))
		{
			Toast.makeText(getApplicationContext(),"Inserted", 10000).show();
		}*/
			}					
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
			
			Log.d("ërror",e.getMessage());
//				e.printStackTrace();
		}
		
		try
		{
			if(ConnectivityReceiver.isConnected())
			{

		int t=sp1.getSelectedItemPosition();
		HashMap<String, String> map = categoryList.get(t);
	//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
			
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("name",name.getText().toString()));
		//params.add(new BasicNameValuePair("amount",amount.getText().toString()));
		params.add(new BasicNameValuePair("from",from));
		params.add(new BasicNameValuePair("to",to));
		params.add(new BasicNameValuePair("category",map.get(TAG_sid).toString()));
		params.add(new BasicNameValuePair("uid",uid));
		JSONObject json = jParser.makeHttpRequest(url_all_categories4, "GET", params);
		String success=json.getString("success");
		total=Integer.parseInt(json.getString("total"));
		
		/*if(success.equals("1"))
		{
			Toast.makeText(getApplicationContext(),"Inserted", 10000).show();
		}*/
					
			}		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
			
			Log.d("ërror",e.getMessage());
//				e.printStackTrace();
		}
		
		if(total+Integer.parseInt(amount.getText().toString())>limit)
			return true;
		else
			return false;
		
		
		
	}
	
	private Boolean checkOverallLimit()
	{
		String from2="",to2="";
		int limit2=0,total2=0;
		try
		{
			if(ConnectivityReceiver.isConnected())
			{

		List<NameValuePair> params2 = new ArrayList<NameValuePair>();
		params2.add(new BasicNameValuePair("uid",uid));
		JSONObject json2 = jParser.makeHttpRequest(url_all_categories5, "GET", params2);
		String success2=json2.getString("success");
		from2=json2.getString("from");
		to2=json2.getString("to");
		limit2=Integer.parseInt(json2.getString("limit"));
		/*if(success.equals("1"))
		{
			Toast.makeText(getApplicationContext(),"Inserted", 10000).show();
		}*/
					
			}		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
			
			Log.d("ërror",e.getMessage());
//				e.printStackTrace();
		}
		
		try
		{
			if(ConnectivityReceiver.isConnected())
			{

		List<NameValuePair> params2 = new ArrayList<NameValuePair>();
		params2.add(new BasicNameValuePair("from",from2));
		params2.add(new BasicNameValuePair("to",to2));
		params2.add(new BasicNameValuePair("uid",uid));
		JSONObject json2 = jParser.makeHttpRequest(url_all_categories6, "GET", params2);
		String success2=json2.getString("success");
		total2=Integer.parseInt(json2.getString("total"));
		/*if(success.equals("1"))
		{
			Toast.makeText(getApplicationContext(),"Inserted", 10000).show();
		}*/
					
			}		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
			
			Log.d("ërror",e.getMessage());
//				e.printStackTrace();
		}
		
		if(total2+Integer.parseInt(amount.getText().toString())>limit2)
			return true;
		else
			return false;
		
		
		
	}


}
