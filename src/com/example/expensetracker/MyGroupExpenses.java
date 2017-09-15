package com.example.expensetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.expensetracker.GroupMembers.InteractiveArrayAdapter;
import com.example.expensetracker.GroupMembers.test;
import com.example.expensetracker.GroupMembers.InteractiveArrayAdapter.ViewHolder;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MyGroupExpenses extends Activity {
	private static String url_all_categories = Connet.url+"myGroupExpenses.php";
	private static String url_all_categories2 = Connet.url+"involvement.php";
	
	ListView lst1;
	
	String uid,gid;
	
	JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	private String TAG_id="gex_id";
	private String TAG_name="name";
	private String TAG_amt="amount";
	private String TAG_date="date";
	private String TAG_added="addedBy";
	private String TAG_status="status";
	
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
		setContentView(R.layout.activity_my_group_expenses);
		
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
		pDialog = new ProgressDialog(MyGroupExpenses.this);
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
 	String zoneAdded = c.getString(TAG_added);
 	String zoneStatus = c.getString(TAG_status);
	// creating new HashMap
	Log.d("All Data: "+i ,zoneName );
	HashMap<String, String> map = new HashMap<String, String>();
	
	// adding each child node to HashMap key => value
	map.put(TAG_id,zoneId);
	map.put(TAG_name, zoneName);
	map.put(TAG_amt, zoneAmt);
	map.put(TAG_date, zoneDate);
	map.put(TAG_added, zoneAdded);
	map.put(TAG_status, zoneStatus);
	
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
			ListAdapter adapter = new InteractiveArrayAdapter(MyGroupExpenses.this, categoryList);
			
			
			// updating listview
					
				lst1.setAdapter(adapter);
				}  });

	}


}

class InteractiveArrayAdapter extends ArrayAdapter<HashMap<String, String>>{

	
	private final ArrayList<HashMap<String, String>> list;
	private final Activity context;

	public InteractiveArrayAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
		super(context, R.layout.my3, list);
		this.context = context;
		this.list = list;
		
	}

	 class ViewHolder {
		protected TextView name,amt,date,added,status;
		//protected ImageView remo;
		//protected CheckBox name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
					
		if (convertView == null) 
		{
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.my3, null);
			final ViewHolder viewHolder = new ViewHolder();
			//viewHolder.name= (CheckBox) view.findViewById(R.id.checkBox1);
			viewHolder.name = (TextView) view.findViewById(R.id.ename);
			viewHolder.amt = (TextView) view.findViewById(R.id.eamt);
			viewHolder.date = (TextView) view.findViewById(R.id.edate);
			viewHolder.added = (TextView) view.findViewById(R.id.addedby); 	
			viewHolder.status = (TextView) view.findViewById(R.id.status);
	       	
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
        holder.added.setText(map.get(TAG_added) );
        holder.status.setText(map.get(TAG_status) );
				
		//holder.remo.setTag(position) ;

	  	return view;

	       	}
	   
	   
}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.my_group_expenses, menu);
//		return true;
//	}

}
