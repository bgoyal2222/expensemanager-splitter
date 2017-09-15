package com.example.expensetracker;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.expensetracker.Add_participant.InteractiveArrayAdapter;
import com.example.expensetracker.Add_participant.InteractiveArrayAdapter.ViewHolder;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseList extends Activity implements OnClickListener{

	
	String from,to,uid;
	
	private static String url_all_categories = Connet.url+"expenseList.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	private static String url_all_categories2 = Connet.url+"deleteExpense.php";
	JSONArray  zone;
	Cursor c1;
	SQLiteDatabase db;
	ListView lst1;
	Button downl;
	JSONparser jParser = new JSONparser();
	private String TAG_id="expense_id";
	private String TAG_expense="expense_name";
	private String TAG_date="date";
	private String TAG_amount="amount";
	String file;
	public static final int progress_bar_type = 0; 
	static String url_all_categories3 = Connet.url+"down_ledger.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	
	
	String keyword;
	private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();

	public static final String TAG_list = "expense_data";
    String success="";

/** Called when the activity is first created. */
 	ProgressDialog pDialog;
	String responseBody;
@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expense_list);
		
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		
		Intent it=getIntent();
		from=it.getStringExtra("from");
		to=it.getStringExtra("to");
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    downl=(Button)findViewById(R.id.button1);
	    downl.setOnClickListener(this);
	    lst1=(ListView)findViewById(R.id.listView1);
	  //  getListView().setBackgroundResource(R.drawable.top);
	    setTitle("Expense list");
	   
	new test().execute("");
	}



	class test extends AsyncTask<String, String, String>
	{
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ExpenseList.this);
			pDialog.setMessage("Wait..");
			
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... arg0) {
			try {
			
				if(ConnectivityReceiver.isConnected())
				{
					List<NameValuePair> params1 = new ArrayList<NameValuePair>();
					
					params1.add(new BasicNameValuePair("from",from));
					params1.add(new BasicNameValuePair("to",to));
					params1.add(new BasicNameValuePair("uid",uid));
		 			//Log.d("1","1");
					JSONObject json1 = jParser.makeHttpRequest(url_all_categories3, "GET", params1);
					//Log.d("data", json1.toString());
					 file = json1.getString("fname");
					
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("from",from));
				params.add(new BasicNameValuePair("to",to));
				params.add(new BasicNameValuePair("uid",uid));
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
	 	String zoneName = c.getString(TAG_expense);
	 	String amount = c.getString(TAG_amount);
	 	String date = c.getString(TAG_date);
		// creating new HashMap
		Log.d("All Data: "+i ,zoneName );
		HashMap<String, String> map = new HashMap<String, String>();
		
		// adding each child node to HashMap key => value
		map.put(TAG_id,zoneId);
		map.put(TAG_expense, zoneName);
		map.put(TAG_amount, amount);
		map.put(TAG_date, date);
		
		// adding HashList to ArrayList
	
			categoryList.add(map);
		Log.d("All Data: "+i, categoryList.get(i).get(TAG_id));

	}
				}
				else
				{
					db=openOrCreateDatabase("expense_tracker.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
Log.d("From date",from);
					c1=db.query("expense",null,"date>='"+from+"' and date<='" + to +"'and status!='2'",null,null,null,"date desc");
					c1.moveToFirst();
					 for (int i = 0; i < c1.getCount(); i++)
					 {
					HashMap<String, String> map = new HashMap<String, String>();
					
					// adding each child node to HashMap key => value
					map.put(TAG_id,c1.getString(0));
					map.put(TAG_expense, c1.getString(1));
					map.put(TAG_amount, c1.getString(2));
					map.put(TAG_date, c1.getString(3));
					Log.d("count",c1.getCount()+" ");
					
					categoryList.add(map);	
					c1.moveToNext();		
						
					 }
					 
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
				ListAdapter adapter = new InteractiveArrayAdapter(ExpenseList.this, categoryList);
				
				// updating listview
						
					lst1.setAdapter(adapter);
					}  });

		}


	}

	class InteractiveArrayAdapter extends ArrayAdapter<HashMap<String, String>>  {

		
		private final ArrayList<HashMap<String, String>> list;
		private final Activity context;

		public InteractiveArrayAdapter(Activity context, ArrayList<HashMap<String, String>> list) {
			super(context, R.layout.my, list);
			this.context = context;
			this.list = list;
			
		}

		 class ViewHolder {
			protected TextView name,amt,d;
			protected ImageView remo;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
						
			if (convertView == null) 
			{
				LayoutInflater inflator = context.getLayoutInflater();
				view = inflator.inflate(R.layout.my, null);
				final ViewHolder viewHolder = new ViewHolder();
				viewHolder.name= (TextView) view.findViewById(R.id.tv1);
				viewHolder.amt = (TextView) view.findViewById(R.id.tv2);
				viewHolder.d = (TextView) view.findViewById(R.id.tv3);
				 viewHolder.remo=(ImageView)view.findViewById(R.id.bfd);
				 viewHolder.remo.setOnClickListener(new View.OnClickListener() {
					
								@Override
					public void onClick(View arg1) {
						// TODO Auto-generated method stub
						int pos=Integer.parseInt(arg1.getTag().toString());
						HashMap<String,String> map1=new HashMap<String,String>();
						map1=list.get(pos);
						// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(),
//								"expense_id" + map1.get(TAG_id), 1000).show();
//					
					try
					{
						if(ConnectivityReceiver.isConnected())
						{
					//HashMap<String, String> map = categoryList.get(t);
				//	Toast.makeText(getApplicationContext(),map.get(TAG_sid), 10000).show();
						
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("expense_id",map1.get(TAG_id).toString()));
					//params.add(new BasicNameValuePair("uid",uid));
					JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
					String success=json.getString("success");
					
					if(success.equals("1"))
					{
						Intent intent = getIntent();
					    finish();
					    startActivity(intent);
						//Toast.makeText(getApplicationContext(),"Deleted", 10000).show();
					}
						}
						else
						{
							ContentValues newValues = new ContentValues();
							newValues.put("status", "2");

						
							db.update("expense", newValues, "expense_id="+map1.get(TAG_id).toString(),null);
								
								Intent intent = getIntent();
							    finish();
							    startActivity(intent);
						}
					}
					catch (Exception e)
					{
						Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
						
						Log.d("ërror",e.getMessage());
//							e.printStackTrace();
					}
				
				   
					
									}
				});
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
	       
	        holder.name.setText(String.format("%-13s",map.get(TAG_expense)) );
	        holder.amt.setText(String.format("%-5s",map.get(TAG_amount)) );
	        holder.d.setText(String.format("%-10s",map.get(TAG_date) ));
					
			holder.remo.setTag(position) ;

		  	return view;

		       	}
		
		   
		   
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(ConnectivityReceiver.isConnected())
		{
			 String info[] ={ Connet.url+"/"+file ,file,"xls"};
			  new DownloadFileFromURL().execute(info);
			 
		
		}
		else
		{			Toast.makeText(getApplicationContext(),"Internet Connection Required..", 10000).show();
		
		    }
		
	}
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case progress_bar_type:
	        pDialog = new ProgressDialog(this);
	        pDialog.setMessage("Downloading file. Please wait...");
	        pDialog.setIndeterminate(false);
	        pDialog.setMax(100);
	        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	        pDialog.setCancelable(true);
	        pDialog.show();
	        return pDialog;
	    default:
	        return null;
	    }
	}
	/*@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.remove)
		{
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid",uid));
			params.add(new BasicNameValuePair("eid",categoryList.get(position).get(TAG_id)));
 			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
		}
		
	}*/
	

	class DownloadFileFromURL extends AsyncTask<String, String, String> {
		 
		   
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        showDialog(progress_bar_type);
	    }
	 
	   
	    @Override
	    protected String doInBackground(String... f_url) {
	        int count;
	        try {
	            URL url = new URL(f_url[0]);
	            URLConnection conection = url.openConnection();
	            conection.connect();
	            // getting file length
	            int lenghtOfFile = conection.getContentLength();
	 
	            // input stream to read file - with 8k buffer
	            InputStream input = new BufferedInputStream(url.openStream(), 8192);
	 
	            // Output stream to write file
	            OutputStream output = new FileOutputStream("/sdcard/Download/"+f_url[1]);
	 
	            byte data[] = new byte[1024];
	 
	            long total = 0;
	 
	            while ((count = input.read(data)) != -1) {
	                total += count;
	                // publishing the progress....
	                // After this onProgressUpdate will be called
	                publishProgress(""+(int)((total*100)/lenghtOfFile));
	 
	                // writing data to file
	                output.write(data, 0, count);
	            }
	 
	            // flushing output
	            output.flush();
	 
	            // closing streams
	            output.close();
	            input.close();
	 
	        } catch (Exception e) {
	            Log.e("Error: ", e.getMessage());
	        }
	 
	        return null;
	    }
	 
	    /**
	     * Updating progress bar
	     * */
	    protected void onProgressUpdate(String... progress) {
	        // setting progress percentage
	        pDialog.setProgress(Integer.parseInt(progress[0]));
	   }
	    protected void onPostExecute(String file_url)
		{
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			
			}
	    
}

	}
