package com.example.expensetracker;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Application;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Bhavesh Goyal on 15/06/16
 */
@SuppressLint("NewApi") public class MyApplication extends Application {
	private static String url_all_categories = Connet.url+"addExpense.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	private static String url_all_categories1 = Connet.url+"deleteExpense.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 
	
	JSONparser jParser = new JSONparser();
	SQLiteDatabase db;
	Cursor c1;
	
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);

Log.d("Welcome", "Hello");
        mInstance = this;
        db=openOrCreateDatabase("expense_tracker.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
	      
        if(checkSavedPreferences()==null)
        {
        	db.execSQL("CREATE TABLE IF NOT EXISTS `expense` (`expense_id` integer primary key autoincrement,  `expense_name` varchar(50) NOT NULL,  `amount` double NOT NULL,  `date` date NOT NULL,  `user_id` int(11) NOT NULL,  `category_id` int(11) NOT NULL,'status' int(11) NOT NULL)");
			
        	db.execSQL("CREATE TABLE IF NOT EXISTS `category` (`category_id` int(11) NOT NULL,  `category_name` varchar(20) NOT NULL)");
        	db.execSQL("INSERT INTO `category` (`category_id`, `category_name`) VALUES (1, 'Bill payments'),(2, 'Stationary'),(3, 'Medical'),(4, 'Other'),(5, 'House hold'),(6, 'Food'),(7, 'Clothes'),(8, 'Entertainment')");
        	//db.execSQL("");
        	savePreferences("firsttym","1");
        	Toast.makeText(getApplicationContext(), "dones" , 1000).show();
            
        }
        if(ConnectivityReceiver.isConnected())
		{
       c1=db.query("expense",null,"status='1'",null,null,null,null);
//    getSupportLoaderManager().initLoader(LOADER_ID, null, this);
       
//         startManagingCursor(c1);
if(c1.getCount()>0)
{
	c1.moveToFirst();
	    
for (int i = 0; i < c1.getCount(); i++)
{
	
	
	
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	
		params.add(new BasicNameValuePair("name",c1.getString(1)));
		params.add(new BasicNameValuePair("amount",c1.getString(2)));
		params.add(new BasicNameValuePair("date",c1.getString(3)));
		params.add(new BasicNameValuePair("category",c1.getString(5)));
		params.add(new BasicNameValuePair("uid",c1.getString(4)));

		JSONObject json = jParser.makeHttpRequest(url_all_categories, "GET", params);
	
	
	// adding each child node to HashMap key => value
	

c1.moveToNext();
	
	
}


}
c1=db.query("expense",null,"status='2'",null,null,null,null);

if(c1.getCount()>0)
{
	c1.moveToFirst();

	for (int i = 0; i < c1.getCount(); i++)
	{
		
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("expense_id",c1.getString(0)));
		//params.add(new BasicNameValuePair("uid",uid));
		JSONObject	json = jParser.makeHttpRequest(url_all_categories1, "GET", params);
				
		
		// adding each child node to HashMap key => value
		

	c1.moveToNext();
		
		
	}


}
		}

//Toast.makeText(getApplicationContext(), c1.getCount()+"" , 1000).show();

        db.close();
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
    private String checkSavedPreferences() {
    	
    	SharedPreferences sp = PreferenceManager
    			.getDefaultSharedPreferences(this);
    	String username=sp.getString("firsttym", null);
    	return username;
    }
    private void savePreferences(String key, String value) {
    	SharedPreferences sp = PreferenceManager
    			.getDefaultSharedPreferences(this);
    	Editor edit = sp.edit();
    	
    	
    	
    	edit.putString(key, value);
    	edit.commit();
    }


}
