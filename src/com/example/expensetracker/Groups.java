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
import android.provider.ContactsContract;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class Groups extends ListActivity implements android.view.View.OnClickListener {
	private static String url_all_categories = Connet.url+"group.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	private String TAG_id="group_id";
	private String TAG_gname="group_name";
	String uid;
	TextView tv1,tv2;
	
//	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();

//    String success="";

/* Called when the activity is first created. */
 	ProgressDialog pDialog;
	String responseBody;

private void savePreferences(String key, String value) {
	SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
	Editor edit = sp.edit();
	
	
	
	edit.putString(key, value);
	edit.commit();
}

	
 @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 	 ActionBar acbar=getActionBar();
		acbar.setDisplayOptions(acbar.getDisplayOptions()|acbar.DISPLAY_SHOW_CUSTOM);
		ImageView iview=new ImageView(acbar.getThemedContext());
		iview.setScaleType(ImageView.ScaleType.CENTER);
		iview.setImageResource(R.drawable.create_group);
		ActionBar.LayoutParams layoutParams=new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,Gravity.RIGHT|Gravity.CENTER_VERTICAL);
		layoutParams.rightMargin=100;
		iview.setLayoutParams(layoutParams);
		acbar.setCustomView(iview);
		iview.setOnClickListener(this);
		

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
	uid=sp.getString("usid", null);

  //  getListView().setBackgroundResource(R.drawable.top);
    setTitle("Groups");
   /* for(int j=0;j<oneContact.getCount();j++)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		oneContact.moveToPosition(j);
		// adding each child node to HashMap key => value;
		map.put(TAG_id,oneContact.getString(0));
		map.put(TAG_name,oneContact.getString(1));
		map.put(TAG_phone,oneContact.getString(2));
		Toast.makeText(getApplicationContext(), "Data "+ oneContact.getString(0)+ oneContact.getString(1)+oneContact.getString(2), 1000).show();
		// adding HashList to ArrayList
		categoryList.add(map);

	}*/
new test().execute("");
// tv=(TextView)findViewById(R.id.tv);
// tv.setText(url_all_categories );
     }

protected void onListItemClick(ListView l,View v,int position,long id)
{
	super.onListItemClick(l, v, position, id);
	Object o=this.getListAdapter().getItem(position);
 String	keyword=o.toString();
		//Toast.makeText(getApplicationContext(), "You Selected:"+categoryList.get(position).get(TAG_id), Toast.LENGTH_LONG).show();
savePreferences("group_id",categoryList.get(position).get(TAG_id));	
Intent it=new Intent(getApplicationContext(),PRH.class);
startActivity(it);

}
 public boolean onCreateOptionsMenu( android.view.Menu menu) {
//	  super.onCreateOptionsMenu(menu);
//	   menu.add("About Us")
//	   	.setIcon(android.R.drawable.ic_menu_info_details)
//	   	.setIntent(new Intent(this, Home.class));
//	   
//	  menu.add("Welcome")
//   	.setIcon(android.R.drawable.ic_menu_info_details)
//   	.setIntent(new Intent(this, AddExpense.class));
//	   
   
	   return true;
}

class test extends AsyncTask<String, String, String>
{
	
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Groups.this);
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
 			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params
 					);
 		
 		//	Log.d("All Products:1 ", "Show1");
  	//	Log.d("All Products:2 ",url_all_categories);
  		zone = json.getJSONArray("group_data");
  		//Log.d("Total Length ","Len"+cuisine.length());
  		//Toast.makeText(getApplicationContext(), cuisine.length()+"Length",1000).show();
Log.d("Error ",json.toString());

Log.d("Array ",zone.length()+"");

  		for (int i = 0; i < zone.length(); i++)
 {
	JSONObject c = zone.getJSONObject(i);
	String usid = c.getString(TAG_id);
 	String gname = c.getString(TAG_gname);
			HashMap<String, String> map = new HashMap<String, String>();
			
			// adding each child node to HashMap key => value
			map.put(TAG_id,usid);
			map.put(TAG_gname, gname);
			
			// adding HashList to ArrayList
				categoryList.add(map);

		}
	
	 // Storing each json item in variable
	
 	// creating new HashMap
	


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
			 * */
			
			ListAdapter adapter = new InteractiveArrayAdapter(Groups.this, categoryList);
			
			setListAdapter(adapter);
				}  });
	}


}



class InteractiveArrayAdapter extends ArrayAdapter<HashMap<String, String>>  {

	
	private final ArrayList<HashMap<String, String>> list;
	private final Activity context;

	public InteractiveArrayAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
		super(context, R.layout.activity_groups, list);
		this.context = context;
		this.list = list;
		
	}

	 class ViewHolder {
		protected TextView gname;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
					
		if (convertView == null) 
		{
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.activity_groups, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.gname= (TextView) view.findViewById(R.id.tv2);
				 
				
		   	
	       	
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
       
        holder.gname.setText(map.get(TAG_gname) );
        
	  	return view;

	       	}
	
	   
	   
}



@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	Intent it=new Intent(getApplicationContext(),CreateGroup.class);
	startActivity(it);
}

}



