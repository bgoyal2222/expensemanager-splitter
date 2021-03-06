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
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryLimit extends Activity implements OnItemSelectedListener,OnClickListener{

	EditText amt,fromd,tod;
	Button fdate,tdate,save;
	Spinner s;
	boolean d1=false,d2=false;
	
	final Calendar dateAndTime=Calendar.getInstance();
	DateFormat fmtDateAndTime=DateFormat.getDateInstance();
	DatePickerDialog.OnDateSetListener d;
	
	private static String url_all_categories = Connet.url+"category.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	private static String url_all_categories2 = Connet.url+"categoryExpense.php";
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
		setContentView(R.layout.activity_category_limit);
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		
		fromd=(EditText)findViewById(R.id.etfrom);
		fromd.setEnabled(false);
		tod=(EditText)findViewById(R.id.etto);
		tod.setEnabled(false);
		amt=(EditText)findViewById(R.id.camt);
		fdate=(Button)findViewById(R.id.bfrom);
		tdate=(Button)findViewById(R.id.bto);
		save=(Button)findViewById(R.id.bsave);
		fdate.setOnClickListener(this);
		tdate.setOnClickListener(this);
		save.setOnClickListener(this);
		s=(Spinner)findViewById(R.id.category);
		
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

	

	class test extends AsyncTask<String, String, String>
	{
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CategoryLimit.this);
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
			Log.d("�rror",e.getMessage());
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
					
					
			        s=(Spinner)findViewById(R.id.category);
//			        ed=(EditText)findViewById(R.id.editText1);
			    	
			        ArrayAdapter<String>  aap = new ArrayAdapter<String>(CategoryLimit.this,android.R.layout.simple_spinner_dropdown_item,lst);
		
//					setListAdapter(adapter);
			        s.setAdapter(aap);

			      		        
//					setListAdapter(adapter);
						}  });
			
		}
		catch (Exception e)
	{
			Log.d("�rror",e.getMessage());
	}
	
			}

	

}

	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		
		
		
		if (arg0.getId()==R.id.bfrom)
		{
			
			d1=true;
			d2=false;
	//DatePickerDialog d1=new DatePickerDialog(DateDialogSampleActivity.this,	d,dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
	DatePickerDialog d1=new DatePickerDialog(CategoryLimit.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
	d1.show();
		}
		else if(arg0.getId()==R.id.bto)
		{
			d2=true;
			d1=false;
			DatePickerDialog d1=new DatePickerDialog(CategoryLimit.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
			d1.show();
		}
		else if(arg0.getId()==R.id.bsave)
		{
			if(!validation())
			{
				try
				{
				int t=s.getSelectedItemPosition();
				HashMap<String, String> map = categoryList.get(t);
		//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("from",fromd.getText().toString()));
				params.add(new BasicNameValuePair("amount",amt.getText().toString()));
				params.add(new BasicNameValuePair("to",tod.getText().toString()));
				params.add(new BasicNameValuePair("category",map.get(TAG_sid).toString()));
				params.add(new BasicNameValuePair("uid",uid));
				JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
				String success=json.getString("success");
			
				if(success.equals("1"))
				{
					Toast.makeText(getApplicationContext(),"Inserted", 10000).show();
				}
						
				}
				catch (Exception e)
				{
					Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
				
					Log.d("�rror",e.getMessage());
//					e.printStackTrace();
				}
			}
		/*else
		{
			TimePickerDialog t1=new TimePickerDialog(AddExpense.this, t, dateAndTime.get(Calendar.HOUR_OF_DAY),dateAndTime.get(Calendar.MINUTE), true);
			t1.show();
		}*/
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
	private void updateLabel() {
		if(d1==true){
			Date from=new Date();
			from=dateAndTime.getTime();
			String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
			//tv1.setText(fmtDateAndTime.format(dateAndTime.getTime()));
			fromd.setText(format);
		}
		else if(d2==true){
			Date from=new Date();
			from=dateAndTime.getTime();
			String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
			//tv2.setText(fmtDateAndTime.format(dateAndTime.getTime()));
			tod.setText(format);
		}
		}
	
	private Boolean validation() {
		Boolean isvalid=false;
				Log.v("TAG","Validation");
				
				if(TextUtils.isEmpty(amt.getText().toString())){
					Log.v("TAG",amt.getText().toString());
					amt.setError("Enter Amount");
					isvalid=true;
					amt.requestFocus();
				}
				 else if (!amt.getText().toString().matches("[0-9]+")) {
					 amt.setError("Accept Numbers Only.");
					 isvalid=true;
					 amt.requestFocus();
				 }
				 else if(Integer.parseInt(amt.getText().toString())<=0)
				 {
					 amt.setError("Amount must be greater than zero.");
					 isvalid=true;
					 amt.requestFocus();
				 }
				return isvalid;
			}

}
