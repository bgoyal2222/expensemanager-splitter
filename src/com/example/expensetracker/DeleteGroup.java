package com.example.expensetracker;

import java.util.ArrayList;
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
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteGroup extends Activity implements OnClickListener{

	Button del;
	String uid,gid;
	
	private static String url_all_categories = Connet.url+"deleteGroup.php";
	
JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	private String TAG_sid="category_id";
	private String TAG_name="category_name";
	
	
	String keyword;
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
		setContentView(R.layout.activity_delete_group);
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		gid=sp.getString("group_id", null);
		
		del=(Button)findViewById(R.id.delete);
		del.setOnClickListener(this);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}
	
	class test extends AsyncTask<String, String, String>
	{
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(DeleteGroup.this);
			pDialog.setMessage("Wait..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uid",uid));
				params.add(new BasicNameValuePair("gid",gid));
	 			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
	 	
	 			Log.d("branch ", json.toString());
		
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
					
					Toast.makeText(getApplicationContext(),"deleted",1000).show();
			        
						}  });
			
		}
		catch (Exception e)
	{
			Log.d("ërror",e.getMessage());
	}
	
			}

	

}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_group, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		new test().execute("");
		
	}

}
