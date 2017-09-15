package com.example.expensetracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.expensetracker.Registration.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetDate extends Activity implements OnClickListener {
	
private static String url_all_categories = Connet.url+"update.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
private static String url_all_categories2 = Connet.url+"selectUser.php";	
	
	
	JSONparser jParser = new JSONparser();

	
	boolean d1=false,d2=false;
	Button b1,b2,b3;
	EditText ed1,fdate,tdate;
	final Calendar dateAndTime=Calendar.getInstance();
	DateFormat fmtDateAndTime=DateFormat.getDateInstance();
	DatePickerDialog.OnDateSetListener d;
	
	ProgressDialog pDialog;

	String uid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_date);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
	    SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
	    
	    setTitle("Update limit and date");
		
		b1=(Button)findViewById(R.id.from);
		b2=(Button)findViewById(R.id.to);
		b3=(Button)findViewById(R.id.set);
		fdate=(EditText)findViewById(R.id.etfdate);
		fdate.setEnabled(false);
		tdate=(EditText)findViewById(R.id.ettdate);
		tdate.setEnabled(false);
		ed1=(EditText)findViewById(R.id.amount);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		
		try {
			if(ConnectivityReceiver.isConnected())
			{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid",uid));
			JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
			ed1.setText(json.getString("limit"));
			fdate.setText(json.getString("from"));
			tdate.setText(json.getString("to"));
			}
			else
			{
				
			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
	}

	

	class test extends AsyncTask<String, String, String>
    {
    	String success;
    	
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(SetDate.this);
    		pDialog.setMessage("Wait..");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();

    	}

    	@Override
    	protected String doInBackground(String... arg0) {
    		try
    		{
    		
    			if(ConnectivityReceiver.isConnected())
    			{
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("limit",ed1.getText().toString()));
    		params.add(new BasicNameValuePair("from",fdate.getText().toString()));
    		params.add(new BasicNameValuePair("to",tdate.getText().toString()));
    		params.add(new BasicNameValuePair("uid",uid));
    		JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
    		 success=json.getString("success");
    		 /*if(success.equals("1"))
    		{
    			Toast.makeText(getApplicationContext(),"Registered", 10000).show();
    		}*/
    			}		
    		}
    		catch (Exception e)
    		{
    			Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
    			
    			Log.d("ërror",e.getMessage());
//    				e.printStackTrace();
    		}
    			


    		return null;
    		
    	}

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url)
    {
    	// dismiss the dialog after getting all products
    	pDialog.dismiss();
    	// updating UI from Background Thread
    	runOnUiThread(new Runnable() {
    		public void run() {
    			if(success.equals("1"))
    			{
    				Toast.makeText(getApplicationContext(),"Updated", 10000).show();
    			}
    			
    				}  });

    	}


    }
    
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId()==R.id.from)
		{
			d1=true;
			d2=false;
	//DatePickerDialog d1=new DatePickerDialog(DateDialogSampleActivity.this,	d,dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
	DatePickerDialog d1=new DatePickerDialog(SetDate.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
	d1.show();
		}
		else if(arg0.getId()==R.id.to)
		{
			d2=true;
			d1=false;
			DatePickerDialog d1=new DatePickerDialog(SetDate.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
			d1.show();
		}
		if(arg0.getId()==R.id.set)
		{
			if(!validation())
			{
				new test().execute("");
			}
		}
	}
	private void updateLabel() {
		if(d1==true){
			Date from=new Date();
			from=dateAndTime.getTime();
			String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
			//tv1.setText(fmtDateAndTime.format(dateAndTime.getTime()));
			fdate.setText(format);
		}
		else if(d2==true){
			Date from=new Date();
			from=dateAndTime.getTime();
			String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
			//tv2.setText(fmtDateAndTime.format(dateAndTime.getTime()));
			tdate.setText(format);
		}
		}
	
	private Boolean validation() {
		Boolean isvalid=false;
				Log.v("TAG","Validation");
				
				if(TextUtils.isEmpty(ed1.getText().toString())){
					Log.v("TAG",ed1.getText().toString());
					ed1.setError("Enter Amount");
					isvalid=true;
					ed1.requestFocus();
				}
				 else if (!ed1.getText().toString().matches("[0-9]+")) {
					 ed1.setError("Accept Numbers Only.");
					 isvalid=true;
					 ed1.requestFocus();
				 }
				 else if(Integer.parseInt(ed1.getText().toString())<=0)
				 {
					 ed1.setError("Amount must be greater than zero.");
					 isvalid=true;
					 ed1.requestFocus();
				 }
				
				return isvalid;
			}
		    

}
