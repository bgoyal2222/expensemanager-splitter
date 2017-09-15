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
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class Add_participant extends Activity implements OnClickListener{
	private static String url_all_categories = Connet.url+"contacts.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	private static String url_all_categories1 = Connet.url+"addparticipant.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 

	JSONArray  zone;
	String uid;
	JSONparser jParser = new JSONparser();
	private String TAG_id="id";
	private String TAG_name="name";
	private String TAG_phone="phone";
	ArrayList<String > chk_List=new ArrayList<String>();

	TextView tv1;
	Button b1,b2;
	ListView lst1;
	CheckBox chck1;
	 Cursor oneContact;
	String keyword;
//	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();

	public static final String TAG_stud = "data";
//    String success="";

/* Called when the activity is first created. */
 	ProgressDialog pDialog;
	String responseBody;
	String[] requestedColumns = {ContactsContract.CommonDataKinds.Phone._ID,
    		ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
    		ContactsContract.CommonDataKinds.Phone.NUMBER
    		
    		};
    
	@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_participant_main);
	
	SharedPreferences sp = PreferenceManager
			.getDefaultSharedPreferences(this);
	uid=sp.getString("usid", null);

	
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    b2=(Button)findViewById(R.id.button1);
    
    lst1=(ListView)findViewById(R.id.listView1);
    b2.setOnClickListener(this);
    //  getListView().setBackgroundResource(R.drawable.top);
    setTitle("Add Participants");
    
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
	
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(Add_participant.this);
		pDialog.setMessage("Wait..");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();

	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
		
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("group_id","1"));
			
			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params
 					);
 		
 		//	Log.d("All Products:1 ", "Show1");
  	//	Log.d("All Products:2 ",url_all_categories);
  		zone = json.getJSONArray(TAG_stud);
  		//Log.d("Total Length ","Len"+cuisine.length());
  		//Toast.makeText(getApplicationContext(), cuisine.length()+"Length",1000).show();
        

  		for (int i = 0; i < zone.length(); i++)
 {
	JSONObject c = zone.getJSONObject(i);
	String usid = c.getString(TAG_id);
 	String usname = c.getString(TAG_name);
 	String usphone = c.getString(TAG_phone);
String []ar={"%"+usphone+"%"};
 	oneContact = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, requestedColumns,
 			ContactsContract.CommonDataKinds.Phone.NUMBER+" LIKE ?"  ,ar ,  null);
    
 	//Cursor cur=managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arr, null, null, null);
 	startManagingCursor(oneContact);
 if(oneContact.getCount()>0)
 	{oneContact.moveToFirst();
 
 	 	
			Log.d(usphone,oneContact.getString(2));
			HashMap<String, String> map = new HashMap<String, String>();
			
			// adding each child node to HashMap key => value
			map.put(TAG_id,usid);
			map.put(TAG_name, usname);
			map.put(TAG_phone,oneContact.getString(2));
			
			// adding HashList to ArrayList
			if(!categoryList.contains(map)&& !usid.equals(uid))
				categoryList.add(map);
 	}
		
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
			
			ListAdapter adapter = new InteractiveArrayAdapter(Add_participant.this, categoryList);
			
			lst1.setAdapter(adapter);
				}  });
	}


}



class InteractiveArrayAdapter extends ArrayAdapter<HashMap<String, String>> implements OnCheckedChangeListener{

	
	private final ArrayList<HashMap<String, String>> list;
	private final Activity context;

	public InteractiveArrayAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
		super(context, R.layout.activity_add_participant, list);
		this.context = context;
		this.list = list;
		
	}

	 class ViewHolder {
		protected TextView phone;
		protected CheckBox name;
	//	protected Button add;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
					
		if (convertView == null) 
		{
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.activity_add_participant, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name= (CheckBox)view.findViewById(R.id.checkBox1);
			viewHolder.phone = (TextView) view.findViewById(R.id.textView1);
		
			//viewHolder.add=(Button)view.findViewById(R.id.button1);
			 //viewHolder.add.setOnClickListener(new View.OnClickListener() {
//				
//							@Override
//				public void onClick(View arg1) {
//					// TODO Auto-generated method stub
//					int pos=Integer.parseInt(arg1.getTag().toString());
//					HashMap<String,String> map1=new HashMap<String,String>();
//					map1=list.get(pos);
//					// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(),
//							"user_id" + map1.get(TAG_id), 1000).show();
//								}
//			});
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
        holder.phone.setText(String.format("%-30s", map.get(TAG_phone) ));
				holder.name.setOnCheckedChangeListener(this);
		holder.name.setTag(map.get(TAG_id));//position) ;

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



@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
String success;
	String st="";
	for (int i=0;i<chk_List.size();i++)
	{
		st=st+","+ chk_List.get(i);
	}
	st=st.substring(1);
try
{


List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("user_ids",st));
params.add(new BasicNameValuePair("group_id","1"));

JSONObject json = jParser.makeHttpRequest(url_all_categories1, "GET", params);

success=json.getString("success");
Toast.makeText(getApplicationContext(), success, Toast.LENGTH_LONG).show();

 
if(success.equals("1"))
{
	Toast.makeText(getApplicationContext(),"Registered", 10000).show();
}
			
}
catch (Exception e)
{
	Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
	
	Log.d("ërror",e.getMessage());
		e.printStackTrace();
}
}

}



