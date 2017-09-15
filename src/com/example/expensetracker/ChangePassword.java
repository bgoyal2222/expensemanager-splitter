package com.example.expensetracker;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity implements OnClickListener{

	private static String url_all_categories = Connet.url+"update.php";	
	private static String url_all_categories2 = Connet.url+"selectUser.php";	
	
	JSONparser jParser = new JSONparser();

	Button change;
	EditText cpass,npass,cnpass;
	String pass;
	
	ProgressDialog pDialog;
	String uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		setTitle("Change Password");

		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
		change=(Button)findViewById(R.id.change);
		cpass=(EditText)findViewById(R.id.cpass);
		npass=(EditText)findViewById(R.id.npass);
		cnpass=(EditText)findViewById(R.id.cnpass);
		change.setOnClickListener(this);
		
		
	}

	

	class test extends AsyncTask<String, String, String>
    {
    	String success;
    	
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(ChangePassword.this);
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
    		params.add(new BasicNameValuePair("npass",npass.getText().toString()));
    		params.add(new BasicNameValuePair("changePassword","1"));
    		params.add(new BasicNameValuePair("uid",uid));
    		JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
    		 success=json.getString("success");
    		 
    		 
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
    				Toast.makeText(getApplicationContext(),"Password changed", 10000).show();
    			}
    			
    			
    				}  });

    	}


    }
    
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.change)
		{
			if(!validation())
			{
				new test().execute("");
			}
		}
		
	}
	private Boolean validation() {
		Boolean isvalid=false;
				Log.v("TAG","Validation");
				
				try {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
					pass=json.getString("pass");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(TextUtils.isEmpty(cpass.getText().toString())){
					cpass.setError("Enter Current password");
					isvalid=true;
					cpass.requestFocus();
				}
				else if (!cpass.getText().toString().matches("[a-zA-Z0-9]{8,}")) {
					cpass.setError("Minimum 8 cahracters required.");
					 isvalid=true;
					 cpass.requestFocus();
				}
				else if (!cpass.getText().toString().equals(pass)) {
					cpass.setError("Password does not match.");
					 isvalid=true;
					 cpass.requestFocus();
				}
				if(TextUtils.isEmpty(npass.getText().toString())){
					npass.setError("Enter New password");
					isvalid=true;
					npass.requestFocus();
				}
				else if (!npass.getText().toString().matches("[a-zA-Z0-9]{8,}")) {
					npass.setError("Minimum 8 cahracters required.");
					 isvalid=true;
					 npass.requestFocus();
				}
				if(TextUtils.isEmpty(cnpass.getText().toString())){
					cnpass.setError("Enter Confirm password");
					isvalid=true;
					cnpass.requestFocus();
				}
				else if (!cnpass.getText().toString().matches("[a-zA-Z0-9]{8,}")) {
					cnpass.setError("Minimum 8 cahracters required.");
					 isvalid=true;
					 cnpass.requestFocus();
				}
				else if (!cnpass.getText().toString().equals(npass.getText().toString())) {
					cnpass.setError("Password does not match.");
					 isvalid=true;
					 cnpass.requestFocus();
				}
				
				return isvalid;
			}

}
