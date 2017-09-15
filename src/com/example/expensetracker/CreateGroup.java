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

public class CreateGroup extends Activity implements OnClickListener {
	
private static String url_all_categories = Connet.url+"creategroup.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	
	
	JSONparser jParser = new JSONparser();

	Button b1;
	EditText ed1;
	
	ProgressDialog pDialog;

	String uid,group_id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
	    SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
	    
	    setTitle("Create Group");
		
		b1=(Button)findViewById(R.id.delete);
		ed1=(EditText)findViewById(R.id.etdate);
		b1.setOnClickListener(this);
		
	}

	

	class test extends AsyncTask<String, String, String>
    {
    	String success;
    	
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(CreateGroup.this);
    		pDialog.setMessage("Wait..");
    		pDialog.setIndeterminate(false);
    		pDialog.setCancelable(true);
    		pDialog.show();

    	}

    	@Override
    	protected String doInBackground(String... arg0) {
    		try
    		{
    		
    		
    		List<NameValuePair> params = new ArrayList<NameValuePair>();
    		params.add(new BasicNameValuePair("name",ed1.getText().toString()));
    		params.add(new BasicNameValuePair("uid",uid));
    		JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
    		 success=json.getString("success");
    		 group_id=json.getString("group_id");
    		 /*if(success.equals("1"))
    		{
    			Toast.makeText(getApplicationContext(),"Registered", 10000).show();
    		}*/
    					
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
    				Toast.makeText(getApplicationContext(),"Created", 10000).show();
    			}
    			
    				}  });

    	}


    }
    
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
			if(arg0.getId()==R.id.delete)
		{
				new test().execute("");
			
		}
	}	    

}
