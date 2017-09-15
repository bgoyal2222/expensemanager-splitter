package com.example.expensetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.zhy.view.*;
import com.zhy.view.CircleMenuLayout.OnMenuItemClickListener;
//import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import com.zhy.view.CircleMenuLayout;
//import com.zhy.view.CircleMenuLayout.OnMenuItemClickListener;
/**
 * <pre>
 * @author zhy 
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class Home extends Activity
{
	SharedPreferences sp;
	Cursor c1;
	SQLiteDatabase db;
	String uid;
	private CircleMenuLayout mCircleMenuLayout;
	private static String url_all_categories = Connet.url+"expenseList1.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	private static String url_all_categories2 = Connet.url+"deleteLimit.php";
	private static String url_all_categories3 = Connet.url+"deleteLimit2.php";
	
	JSONArray  zone;
	private String TAG_id="expense_id";
	private String TAG_expense="expense_name";
	private String TAG_date="date";
	private String TAG_amount="amount";
	private String TAG_userid="user_id";
	private String TAG_categoryid="category_id";
	ContentValues cv;
	public static final String TAG_list = "expense_data";
private String TAG_id2="limit_id";
	
	public static final String TAG_list2 = "limit_data";
	
	JSONparser jParser = new JSONparser();
	
	private String[] mItemTexts = new String[] { "Add Expense ", "Groups", "View Expenses",
			"View Expense Reports", "Set Limit", "Change Password" };
	private int[] mItemImgs = new int[] { R.drawable.addexpense,
			R.drawable.groups, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	@SuppressLint("ShowToast") @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    String st="";
		
db=openOrCreateDatabase("expense_tracker.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
//		   
//	       c1=db.query("expense",null,null,null,null,null,null);
//
//	        	          startManagingCursor(c1);
//	
//	
//	 Toast.makeText(getApplicationContext(), c1.getCount()+"" , 1000).show();



		sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		setContentView(R.layout.activity_home);
		
		if(ConnectivityReceiver.isConnected())
		{
			try {
				
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uid",uid));
	 			JSONObject json = jParser.makeHttpRequest(url_all_categories2, "GET", params);
	 	
	 			Log.d("branch ", json.toString());
	 		
	  		zone = json.getJSONArray(TAG_list2);
	  		
	 for (int i = 0; i < zone.length(); i++)
	 {	
		JSONObject c = zone.getJSONObject(i);

		String zoneId = c.getString(TAG_id2);
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(TAG_id2,zoneId);
		st=st+","+ map.get(TAG_id2);
	 			
		
	}
	}
		catch (Exception e)
	{
			Log.d("ërror",e.getMessage());
	}
			
			try
			{
				
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("limit_id",st));
			JSONObject json = jParser.makeHttpRequest(url_all_categories3, "GET", params);
			String success=json.getString("success");
			
			if(success.equals("1"))
			{
				Toast.makeText(getApplicationContext(),"deleted", 10000).show();
			}
						
			}
			catch (Exception e)
			{
				Toast.makeText(getApplicationContext(),e.getMessage()+"error", 10000).show();
				
				Log.d("ërror",e.getMessage());
//					e.printStackTrace();
			}
			

			db.execSQL("delete from expense;");
			try {
			
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("uid",uid));
	 			JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);

	 		//	Log.d("All Products:1 ", "Show1");
	  	//	Log.d("All Products:2 ",url_all_categories);
	  		zone = json.getJSONArray(TAG_list);
	  		//Log.d("Total Length ","Len"+cuisine.length());
	  		//Toast.makeText(getApplicationContext(), cuisine.length()+"Length",1000).show();
	 for (int i = 0; i < zone.length(); i++)
	 {
		JSONObject c = zone.getJSONObject(i);

		 // Storing each json item in variable
				 cv=new ContentValues();
		 cv.put("expense_id",Integer.parseInt(c.getString(TAG_id)));			
		 cv.put("expense_name",c.getString(TAG_expense));
		cv.put("amount", Double.parseDouble(c.getString(TAG_amount)));
		cv.put("date", c.getString(TAG_date));
		cv.put("user_id", Integer.parseInt(c.getString(TAG_userid)));
		cv.put("category_id",Integer.parseInt(c.getString(TAG_categoryid)));
		cv.put("status","0");
		
		db.insert("expense",null,cv);
		cv.clear();
	
	}
	 db.close();
	}
		catch (Exception e)
	{
		e.printStackTrace();
	}

		}
		//setContentView(R.layout.activity_main02);

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
				if(pos==0){
					Intent it=new Intent(getApplicationContext(),AddExpense.class);
					
				 startActivity(it);
				 return;
					}
				else if(pos==1){
					Intent it=new Intent(getApplicationContext(),Groups.class);
					
				 startActivity(it);
				 return;
					}
				else if(pos==3){
					Intent it=new Intent(getApplicationContext(),PieFromTo.class);
					
				 startActivity(it);
				 return;
					}
				else if(pos==2){
					Intent it=new Intent(getApplicationContext(),UserExpense.class);
					
				 startActivity(it);
				 return;
					}
				else if(pos==4){
					Intent it=new Intent(getApplicationContext(),SetLimit2.class);
					
				 startActivity(it);
				 return;
					}

				else if(pos==5){
					Intent it=new Intent(getApplicationContext(),ChangePassword.class);
					
				 startActivity(it);
				 return;
					}
				Toast.makeText(Home.this, mItemTexts[pos],
						Toast.LENGTH_SHORT).show();

			}
			
			@Override
			public void itemCenterClick(View view)
			{
//				Toast.makeText(Home.this,
//						"you can do something just like ccb  ",
//						Toast.LENGTH_SHORT).show();
				//Toast.makeText(getApplicationContext(), sp.getString("usname", null), 1000).show();
					Editor edit = sp.edit();
				
				
				
					edit.clear();
				edit.commit();
				Intent it=new Intent(getApplicationContext(),Login.class);
				
				 startActivity(it);
				
			}
		});
		
	}

}
