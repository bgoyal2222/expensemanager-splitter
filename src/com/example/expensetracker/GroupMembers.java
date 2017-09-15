package com.example.expensetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.expensetracker.ExpenseList.InteractiveArrayAdapter;
import com.example.expensetracker.ExpenseList.test;
import com.example.expensetracker.ExpenseList.InteractiveArrayAdapter.ViewHolder;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupMembers extends Activity implements OnClickListener{
	
	
	String gex_id;
	String amount;
	String user_id;
	String gid;
	
	Button b1;
	ListView lst1;
	
	private static String url_all_categories = Connet.url+"groupMembers.php";
	private static String url_all_categories2 = Connet.url+"involved.php";
	JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	private String TAG_id="user_id";
	private String TAG_name="user_name";
	
	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();
    ArrayList<String > chk_List=new ArrayList<String>();

	public static final String TAG_list = "group_members";
    String success="";

/** Called when the activity is first created. */
 	ProgressDialog pDialog;
	String responseBody;
@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_members);
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		gid=sp.getString("group_id", null);
		
		Intent it=getIntent();
		gex_id=it.getStringExtra("gex_id");
		amount=it.getStringExtra("amount");
		user_id=it.getStringExtra("user_id");
		
		b1=(Button)findViewById(R.id.got);
		b1.setOnClickListener(this);
		lst1=(ListView)findViewById(R.id.listView1);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);

	  //  getListView().setBackgroundResource(R.drawable.top);
	    setTitle("Group Members");
	   
	new test().execute("");
	}

class test extends AsyncTask<String, String, String>
{
	
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(GroupMembers.this);
		pDialog.setMessage("Wait..");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();

	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
		
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			//params.add(new BasicNameValuePair("from",from));
			params.add(new BasicNameValuePair("gid",gid));
 			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
 		Log.d("branch ", json.toString());
 		//	Log.d("All Products:1 ", "Show1");
  	//	Log.d("All Products:2 ",url_all_categories);
  		zone = json.getJSONArray(TAG_list);
  		//Log.d("Total Length ","Len"+cuisine.length());
  		//Toast.makeText(getApplicationContext(), cuisine.length()+"Length",1000).show();
 for (int i = 0; i < zone.length(); i++)
 {
	JSONObject c = zone.getJSONObject(i);

	 // Storing each json item in variable
	String zoneId = c.getString(TAG_id);
 	String zoneName = c.getString(TAG_name);
 
	// creating new HashMap
	Log.d("All Data: "+i ,zoneName );
	HashMap<String, String> map = new HashMap<String, String>();
	
	// adding each child node to HashMap key => value
	map.put(TAG_id,zoneId);
	map.put(TAG_name, zoneName);
	
	// adding HashList to ArrayList
	if(!categoryList.contains(map))
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
			ListAdapter adapter = new InteractiveArrayAdapter(GroupMembers.this, categoryList);
			
			// updating listview
					
				lst1.setAdapter(adapter);
				}  });

	}


}

class InteractiveArrayAdapter extends ArrayAdapter<HashMap<String, String>> implements OnCheckedChangeListener{

	
	private final ArrayList<HashMap<String, String>> list;
	private final Activity context;

	public InteractiveArrayAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
		super(context, R.layout.my2, list);
		this.context = context;
		this.list = list;
		
	}

	 class ViewHolder {
		//protected TextView name,amt;
		//protected ImageView remo;
		protected CheckBox name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
					
		if (convertView == null) 
		{
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.my2, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name= (CheckBox) view.findViewById(R.id.checkBox1);
		
			/*viewHolder.amt = (TextView) view.findViewById(R.id.tv2);
			 viewHolder.remo=(ImageView)view.findViewById(R.id.bfd);
			 viewHolder.remo.setOnClickListener(new View.OnClickListener() {
				
							@Override
				public void onClick(View arg1) {
					// TODO Auto-generated method stub
					int pos=Integer.parseInt(arg1.getTag().toString());
					HashMap<String,String> map1=new HashMap<String,String>();
					map1=list.get(pos);
					// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
							"user_id" + map1.get(TAG_id), 1000).show();
				
				try
				{
				//HashMap<String, String> map = categoryList.get(t);
			//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
					
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user_id",map1.get(TAG_id).toString()));
				//params.add(new BasicNameValuePair("uid",uid));
				JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
				String success=json.getString("success");
				
				if(success.equals("1"))
				{
					Toast.makeText(getApplicationContext(),"Deleted", 10000).show();
				}
							
				}
				catch (Exception e)
				{
					Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
					
					Log.d("ërror",e.getMessage());
//						e.printStackTrace();
				}
			
			   
				
								}
			});*/
			//viewHolder.photo = (ImageView) view.findViewById(R.id.imageView1);	
	       	
	       	
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
       
		holder.name.setText(String.format("%-30s",  map.get(TAG_name)) );
		holder.name.setOnCheckedChangeListener(this);
		holder.name.setTag(map.get(TAG_id));
		
        //holder.name.setText(map.get(TAG_name) );
				
		//holder.remo.setTag(position) ;

	  	return view;

	       	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
if (arg0.isChecked())
{
	chk_List.add(arg0.getTag().toString());
}
else
{
	chk_List.remove(arg0.getTag().toString());
	
}
	}
	   
	   
}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.group_members, menu);
//		return true;
//	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String st="";
		for (int i=0;i<chk_List.size();i++)
		{
			st=st+","+ chk_List.get(i);
		}
		st=st.substring(1);
		//Toast.makeText(getApplicationContext(), st, Toast.LENGTH_LONG).show();
		
		try
		{
		//HashMap<String, String> map = categoryList.get(t);
	//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
			
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_id",st));
		params.add(new BasicNameValuePair("gex_id",gex_id));
		params.add(new BasicNameValuePair("addedBy",user_id));
		//params.add(new BasicNameValuePair("uid",uid));
		JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
		String success=json.getString("success");
		
		if(success.equals("1"))
		{
			Toast.makeText(getApplicationContext(),"inserted", 10000).show();
		}
					
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
			
			Log.d("ërror",e.getMessage());
//				e.printStackTrace();
		}
	}
	

}
