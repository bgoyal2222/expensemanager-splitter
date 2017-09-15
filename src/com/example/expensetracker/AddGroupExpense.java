package com.example.expensetracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.expensetracker.AddExpense.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddGroupExpense extends Activity implements OnItemSelectedListener,OnClickListener{

	
	EditText name,amount,date;
	Button b1,b2;
	final Calendar dateAndTime=Calendar.getInstance();
	DateFormat fmtDateAndTime=DateFormat.getDateInstance();
	DatePickerDialog.OnDateSetListener d;
	//TimePickerDialog.OnTimeSetListener t;
	
	Spinner sp1;
	private static String url_all_categories = Connet.url+"category.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	private static String url_all_categories2 = Connet.url+"groupExpense.php";
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
		setContentView(R.layout.activity_add_group_expense);
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		
		amount=(EditText)findViewById(R.id.gamt);
		   name=(EditText)findViewById(R.id.gexp);
		
		b1=(Button)findViewById(R.id.gset);
		b2=(Button)findViewById(R.id.gadd);
		date=(EditText)findViewById(R.id.etgdate);
		date.setEnabled(false);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		sp1=(Spinner)findViewById(R.id.spinner1);
		
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
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_group_expense, menu);
//		return true;
//	}
	
	class test extends AsyncTask<String, String, String>
	{
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AddGroupExpense.this);
			pDialog.setMessage("Wait..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {
			try {
			
				
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
					
					
			        sp1=(Spinner)findViewById(R.id.spinner1);
//			        ed=(EditText)findViewById(R.id.editText1);
			    	
			        ArrayAdapter<String>  aap = new ArrayAdapter<String>(AddGroupExpense.this,android.R.layout.simple_spinner_dropdown_item,lst);
		
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
		if (arg0.getId()==R.id.gset)
		{
			
	//DatePickerDialog d1=new DatePickerDialog(DateDialogSampleActivity.this,	d,dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
	DatePickerDialog d1=new DatePickerDialog(AddGroupExpense.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
	d1.show();
		}
		else if(arg0.getId()==R.id.gadd)
		{
			if(!validation())
			{
			try
			{
			int t=sp1.getSelectedItemPosition();
			HashMap<String, String> map = categoryList.get(t);
		//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
				
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name",name.getText().toString()));
			params.add(new BasicNameValuePair("amount",amount.getText().toString()));
			params.add(new BasicNameValuePair("date",date.getText().toString()));
			params.add(new BasicNameValuePair("category",map.get(TAG_sid).toString()));
			params.add(new BasicNameValuePair("uid",uid));
			JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
			String success=json.getString("success");
			String gex_id=json.getString("gex_id");
			String amount=json.getString("amount");
			String user_id=json.getString("user_id");
			
			if(success.equals("1"))
			{
				//Toast.makeText(getApplicationContext(),gex_id, 10000).show();
				Intent it=new Intent(getApplicationContext(),GroupMembers.class);
				it.putExtra("gex_id",gex_id);
				it.putExtra("amount",amount);
				it.putExtra("user_id",user_id);
				startActivity(it);
			}
						
			}
			catch (Exception e)
			{
				Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
				
				Log.d("ërror",e.getMessage());
//					e.printStackTrace();
			}
			}
		}
		/*else
		{
			TimePickerDialog t1=new TimePickerDialog(AddExpense.this, t, dateAndTime.get(Calendar.HOUR_OF_DAY),dateAndTime.get(Calendar.MINUTE), true);
			t1.show();
		}*/

	}
	private void updateLabel() {
		//tv1.setText(fmtDateAndTime.format(dateAndTime.getTime()));
		
		Date from=new Date();
		from=dateAndTime.getTime();
		String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
		//tv1.setText(fmtDateAndTime.format(dateAndTime.getTime()));
		date.setText(format);
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


}
