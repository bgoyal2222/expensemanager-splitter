package com.example.expensetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.expensetracker.MyGroupExpenses.InteractiveArrayAdapter;
import com.example.expensetracker.MyGroupExpenses.test;
import com.example.expensetracker.MyGroupExpenses.InteractiveArrayAdapter.ViewHolder;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Receivable extends Activity {
	private static String url_all_categories = Connet.url+"receivable.php";
	private static String url_all_categories2 = Connet.url+"involvement.php";
	private static String url_all_categories3 = Connet.url+"received.php";
	
	ListView lst1;
	
	String uid,gid;
	
	JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	private String TAG_id="gex_id";
	private String TAG_name="name";
	private String TAG_amt="amount";
	private String TAG_date="date";
	private String TAG_user="user_name";
	private String TAG_uid="user_id";
	//private String TAG_status="status";
	
	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();
    ArrayList<String > chk_List=new ArrayList<String>();

	public static final String TAG_list = "my_data";
    String success="";

/** Called when the activity is first created. */
 	ProgressDialog pDialog;
	String responseBody;
@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receivable);
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		gid=sp.getString("group_id", null);
		
		lst1=(ListView)findViewById(R.id.listView1);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);

	    setTitle("My expenses");
	   
	new test().execute("");
	}

class test extends AsyncTask<String, String, String>
{
	
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Receivable.this);
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
 		//	Log.d("All Products:1 ", "Show1");
  	//	Log.d("All Products:2 ",url_all_categories);
  		zone = json.getJSONArray(TAG_list);
  		//Log.d("Total Length ","Len"+cuisine.length());
  		//Toast.makeText(getApplicationContext(), cuisine.length()+"Length",1000).show();
  		JSONObject json2;
  		for (int i = 0; i < zone.length(); i++)
 {
	JSONObject c = zone.getJSONObject(i);

	 // Storing each json item in variable
	String zoneId = c.getString(TAG_id);
	
	List<NameValuePair> params2 = new ArrayList<NameValuePair>();
	params2.add(new BasicNameValuePair("gex_id",zoneId));
	json2 = jParser.makeHttpRequest(url_all_categories2, "GET", params2);
	String count=json2.getString("count");
	int Amt = Integer.parseInt(c.getString(TAG_amt))/Integer.parseInt(count);
	String zoneAmt=Amt+" ";
	Log.d("Amount",Amt+"");
	
	//String zoneAmt=c.getString(TAG_amt);
 	String zoneName = c.getString(TAG_name);
 	String zoneDate = c.getString(TAG_date);
 	String zoneUser = c.getString(TAG_user);
 	String zoneUid = c.getString(TAG_uid);
 	//String zoneStatus = c.getString(TAG_status);
	// creating new HashMap
	Log.d("All Data: "+i ,zoneName );
	HashMap<String, String> map = new HashMap<String, String>();
	
	// adding each child node to HashMap key => value
	map.put(TAG_id,zoneId);
	map.put(TAG_name, zoneName);
	map.put(TAG_amt, zoneAmt);
	map.put(TAG_date, zoneDate);
	map.put(TAG_user, zoneUser);
	map.put(TAG_uid, zoneUid);
	//map.put(TAG_status, zoneStatus);
	
	// adding HashList to ArrayList
		categoryList.add(map);
	Log.d("All Data: "+i, categoryList.get(i).get(TAG_id));

}
}
	catch (Exception e)
{
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
			
			 * */
			ListAdapter adapter = new InteractiveArrayAdapter(Receivable.this, categoryList);
			
			// updating listview
					
				lst1.setAdapter(adapter);
				}  });

	}


}

class InteractiveArrayAdapter extends ArrayAdapter<HashMap<String, String>>{

	
	private final ArrayList<HashMap<String, String>> list;
	private final Activity context;

	public InteractiveArrayAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
		super(context, R.layout.my4, list);
		this.context = context;
		this.list = list;
		
	}

	 class ViewHolder {
		protected TextView name,amt,date,user,status;
		protected Button got;
		//protected ImageView remo;
		//protected CheckBox name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
					
		if (convertView == null) 
		{
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.my4, null);
			final ViewHolder viewHolder = new ViewHolder();
			//viewHolder.name= (CheckBox) view.findViewById(R.id.checkBox1);
			viewHolder.name = (TextView) view.findViewById(R.id.expname);
			viewHolder.amt = (TextView) view.findViewById(R.id.expamt);
			viewHolder.date = (TextView) view.findViewById(R.id.expdate);
			viewHolder.user = (TextView) view.findViewById(R.id.receive); 	
			//viewHolder.status = (TextView) view.findViewById(R.id.status);
			viewHolder.got = (Button) view.findViewById(R.id.got);
			viewHolder.got.setTag(position);
viewHolder.got.setOnClickListener(new View.OnClickListener() {
				
				@Override
	public void onClick(View arg1) {
		// TODO Auto-generated method stub
		int pos=Integer.parseInt(arg1.getTag().toString());
		HashMap<String,String> map1=new HashMap<String,String>();
		map1=list.get(pos);
		// TODO Auto-generated method stub
	Toast.makeText(getApplicationContext(),"gex_id" + map1.get(TAG_id), 1000).show();
	
	try
	{
	//HashMap<String, String> map = categoryList.get(t);
//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
		
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("gex_id",map1.get(TAG_id).toString()));
	params.add(new BasicNameValuePair("user_id",map1.get(TAG_uid).toString()));
	//params.add(new BasicNameValuePair("uid",uid));
	JSONObject json = jParser.makeHttpRequest(url_all_categories3, "GET", params);
	String success=json.getString("success");
	Log.d("Success",success);
	if(success.equals("1"))
	{
		Toast.makeText(getApplicationContext(),"updated", 10000).show();
	}
				
	}
	catch (Exception e)
	{
		Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
		
		Log.d("ërror",e.getMessage());
//			e.printStackTrace();
	}

   
	
					}
});
	       	
			view.setTag(viewHolder);
		
		}
		else { 
			view = convertView;
			
			 	}
		
		ViewHolder holder = (ViewHolder) view.getTag();
  		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map=list.get(position);		
		
		//Drawable drawable = LoadImageFromWeb(myconnect.imgurl +map.get(TAG_photo));
		  
       //holder.photo.setImageDrawable(drawable);
       
		/*holder.name.setText(String.format("%-30s",  map.get(TAG_name)) );
		holder.name.setOnCheckedChangeListener(this);
		holder.name.setTag(map.get(TAG_id));*/
		holder.amt.setText(map.get(TAG_amt) );
        holder.name.setText(map.get(TAG_name) );
        holder.date.setText(map.get(TAG_date) );
        holder.user.setText(map.get(TAG_user) );
        //holder.status.setText(map.get(TAG_status) );
				
		//holder.remo.setTag(position) ;

	  	return view;

	       	}
	   
	   
}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.receivable, menu);
//		return true;
//	}

}
