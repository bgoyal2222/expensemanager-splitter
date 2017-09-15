package com.example.expensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends Activity implements OnClickListener{
	
	private static String url_all_categories = Connet.url+"expense.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	
	JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	
	EditText ed1;
	EditText ed2;
	EditText ed3;
	EditText ed4;
	EditText ed5;
	Button btn,btn2;
	
//	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();

	public static final String TAG_stud = "userData";
//    String success="";

/* Called when the activity is first created. */
 	ProgressDialog pDialog;
	String responseBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

  //  getListView().setBackgroundResource(R.drawable.top);
    setTitle("Registration");
   ed1=(EditText)findViewById(R.id.uname);
   ed2=(EditText)findViewById(R.id.email);
   ed3=(EditText)findViewById(R.id.pass);
   ed4=(EditText)findViewById(R.id.contact);
   ed5=(EditText)findViewById(R.id.confirmpass);
   btn=(Button)findViewById(R.id.register);
   btn.setOnClickListener(this);
   
   btn2=(Button)findViewById(R.id.back);
   btn2.setOnClickListener(this);

   
   
//Toast.makeText(this,categoryList.size()+"", 1000).show();
// tv=(TextView)findViewById(R.id.tv);
// tv.setText(url_all_categories );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
        return true;
    }

    class test extends AsyncTask<String, String, String>
    {
    	String msg="",success;
    	
    	protected void onPreExecute() {
    		super.onPreExecute();
    		pDialog = new ProgressDialog(Registration.this);
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
    		params.add(new BasicNameValuePair("email",ed2.getText().toString()));
    		params.add(new BasicNameValuePair("pass",ed3.getText().toString()));
    		params.add(new BasicNameValuePair("contact",ed4.getText().toString()));
    		JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
    		 success=json.getString("success");
    		msg=json.getString("message");
    		 /*if(success.equals("1"))
    		{
    			Toast.makeText(getApplicationContext(),"Registered", 10000).show();
    		}*/
    					
    		}
    		catch (Exception e)
    		{
    			//Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
    			
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
    			if(success.equals("0"))
        		{
        			if(msg.equals("email"))
        			{		ed2.setError("Email Already Registered...");
       			 
       			 ed2.requestFocus();
        			}
        			else if(msg.equals("email"))
        			{ed4.setError("Phone No. Already Registered..");
          			 
          			 ed4.requestFocus();
           		
        			}
        			
        		}
    			else if(success.equals("1"))
    			{
    				Toast.makeText(getApplicationContext(),"Registered", 10000).show();
    			}
    			
    				}  });

    	}


    }
    
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0.getId()==R.id.register)
		{
			if(!validation())
			{
				new test().execute("");
				/*Intent it=new Intent(getApplicationContext(),Login.class);
//				it.putExtra("user",ed1.getText().toString());
				startActivity(it);*/
			}
		}
		else if(arg0.getId()==R.id.back)
		{
			Intent it=new Intent(getApplicationContext(),Login.class);
			startActivity(it);
		}
	}
	
	private Boolean validation() {
Boolean isvalid=false;
		Log.v("TAG","Validation");
		
		if(TextUtils.isEmpty(ed1.getText().toString())){
			Log.v("TAG",ed1.getText().toString());
			ed1.setError("Enter Username");
			isvalid=true;
			ed1.requestFocus();
		}
		 else if (!ed1.getText().toString().matches("[a-zA-Z ]+")) {
			 ed1.setError("Accept Alphabets Only.");
			 isvalid=true;
			 ed1.requestFocus();
		 }
		
		
		if(TextUtils.isEmpty(ed2.getText().toString())){
			ed2.setError("Enter Email");
			isvalid=true;
			ed2.requestFocus();
		}
		else if (!ed2.getText().toString().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			ed2.setError("Enter Valid Email.");
			 isvalid=true;
			 ed2.requestFocus();
		}
		
		if(TextUtils.isEmpty(ed3.getText().toString())){
			ed3.setError("Enter password");
			isvalid=true;
			ed3.requestFocus();
		}
		else if (!ed3.getText().toString().matches("[a-zA-Z0-9]{8,}")) {
			ed3.setError("Minimum 8 cahracters required.");
			 isvalid=true;
			 ed3.requestFocus();
		}
		
		if(TextUtils.isEmpty(ed4.getText().toString())){
			ed4.setError("Enter Mobilenumber");
			isvalid=true;
			ed4.requestFocus();
		}
		else if (!ed4.getText().toString().matches("[0-9 ]{10}")) {
			ed4.setError("Number must be of 10 digits.");
			 isvalid=true;
			 ed4.requestFocus();
		}
		
		if (!ed5.getText().toString().equals(ed3.getText().toString())) {
			ed5.setError("Password does not match.");
			 isvalid=true;
			 ed5.requestFocus();
		}
		return isvalid;
	}
    
}
