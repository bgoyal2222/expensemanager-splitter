package com.example.expensetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;



import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;



@SuppressLint("NewApi") public class Login extends Activity implements OnClickListener {
	private static String url_all_categories = Connet.url+"login.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	
	EditText ed1,ed2;
	CheckBox rem;
	Button b1,b2;
	TextView txt1;
	
	String keyword;
//	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();

	public static final String TAG_stud = "data";
//    String success="";

/* Called when the activity is first created. */
 	ProgressDialog pDialog;
	String responseBody;
 @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    checkSavedPreferences();
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    setTitle("Login");
    ed1=(EditText)findViewById(R.id.email);
    ed2=(EditText)findViewById(R.id.pas);
    b1=(Button)findViewById(R.id.login);
    b2=(Button)findViewById(R.id.regist);
    txt1=(TextView)findViewById(R.id.name);
    rem=(CheckBox)findViewById(R.id.remem);
    b1.setOnClickListener(this);
    b2.setOnClickListener(this);
    //  getListView().setBackgroundResource(R.drawable.top);
    
   

// tv=(TextView)findViewById(R.id.tv);
// tv.setText(url_all_categories );
     }
	
private Boolean validation() {
	
	Log.v("TAG","Validation");
	Boolean isvalid=false;
	
	
	if(TextUtils.isEmpty(ed1.getText().toString())){
		ed1.setError("Enter Email");
		isvalid=true;
		ed1.requestFocus();
	}
	else if (!ed1.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
		ed1.setError("Enter Valid Email.");
		 isvalid=true;
		 ed1.requestFocus();
	}
	if(TextUtils.isEmpty(ed2.getText().toString())){
		ed2.setError("Enter Password");
		isvalid=true;
		ed2.requestFocus();
	}
	//"/^((?=(.*[\d0-9\@\&#\$\?\%!\|(){}[\]]){2,})(?=(.*[a-zA-Z]){2,}).{8,})$/"
	return isvalid;
	}
	


 public boolean onCreateOptionsMenu( android.view.Menu menu) {
	  // super.onCreateOptionsMenu(menu);
	  // menu.add("About Us")
	   	//.setIcon(android.R.drawable.ic_menu_info_details)
	   	//.setIntent(new Intent(this, About.class));
	   
	  
	 
	 //menu.add("Welcome")
   	//.setIcon(android.R.drawable.ic_menu_info_details)
   	//.setIntent(new Intent(this, Welcome.class));
	   
   
	   return true;
}

class test extends AsyncTask<String, String, String>
{
	String id,name,suc="0";
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Login.this);
		pDialog.setMessage("Wait..");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();

	}

	@Override
	protected String doInBackground(String... arg0) {
		
		try {
		
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("email",ed1.getText().toString()));
			params.add(new BasicNameValuePair("password",ed2.getText().toString()));
			//Toast.makeText(getBaseContext(), "Success", 1000).show();
			
			Log.d("user abd pass ", ed1.getText().toString()+ ", "+ ed2.getText().toString());
			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params
 					);
 		suc=json.getString("success");
 		name=json.getString("name");
 		id=json.getString("id");
 		//	Log.d("All Products:1 ", "Show1");
  	//	Log.d("All Products:2 ",url_all_categories);
  		
  		//Log.d("Total Length ","Len"+cuisine.length());
  		//Toast.makeText(getApplicationContext(), cuisine.length()+"Length",1000).show();
 
		
		}
	catch (Exception e)
{
	Log.d("ërror",e.getMessage());
		e.printStackTrace();
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
 			/**
			 * Updating parsed JSON data into ListView
			 * 
			 * */
			
			if(suc.equals("1"))
	 		{
				if (rem.isChecked())
				{	savePreferences("usname", name);
				
				}
				Intent it=new Intent(getApplicationContext(),Home.class);
					it.putExtra("usr",name);
					savePreferences("usid", id);
				startActivity(it);
	 		}
			else
				
				txt1.setTextColor(Color.RED);
				txt1.setText("Invalid Email or Password..");
		 	
				}  });
	}


}
private void checkSavedPreferences() {
	
	SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
	String username=sp.getString("usname", null);
	if (username!=null)
	 {
		Intent it=new Intent(getApplicationContext(),Home.class);
		it.putExtra("usr",username);
		
		startActivity(it);
	}
}

private void savePreferences(String key, String value) {
	SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
	Editor edit = sp.edit();
	
	
	
	edit.putString(key, value);
	edit.commit();
}


@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	if(arg0.getId()==R.id.login)
	{
	if(!validation())
	{
		new test().execute("");
		
	}
	}
	if(arg0.getId()==R.id.regist)
	{
		Intent it=new Intent(getApplicationContext(),Registration.class);
		
		
		startActivity(it);
	
	}
}
}
