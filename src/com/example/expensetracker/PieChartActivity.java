
package com.example.expensetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.expensetracker.ExpenseList.test;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;


import java.util.ArrayList;

public class PieChartActivity extends Activity implements 
        OnChartValueSelectedListener {
	private static String url_all_categories = Connet.url+"categoryTotal.php";//"http://u369276347.cuccfree.com/androidcategory.php";//http://10.0.2.2/Project 

JSONArray  zone;
	
	JSONparser jParser = new JSONparser();
	private String TAG_category="category_name";
	private String TAG_sum="total";
		
String uid,from,to;
private static final String TAG_SUCCESS = "success";
ArrayList<HashMap<String, String>>categoryList=new ArrayList<HashMap<String,String>>();

public static final String TAG_list = "category_total";
String success="";

/** Called when the activity is first created. */
	ProgressDialog pDialog;
String responseBody;
    private PieChart mChart;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_piechart);
        SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		uid=sp.getString("usid", null);
		
		Intent it=getIntent();
		from=it.getStringExtra("from");
		to=it.getStringExtra("to");
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);

	  //  getListView().setBackgroundResource(R.drawable.top);
	    setTitle("Category Total");
	    new test().execute("");

            mChart = (PieChart) findViewById(R.id.chart1);

        // change the color of the center-hole
//        mChart.setHoleColor(Color.rgb(235, 235, 235));
        mChart.setHoleColorTransparent(true);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setValueTypeface(tf);
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        mChart.setHoleRadius(60f);

        mChart.setDescription("");

        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);

        mChart.setDrawHoleEnabled(true);

        mChart.setRotationAngle(0);

        // draws the corresponding description value into the slice
        mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInCha0rt(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

        mChart.setCenterText("Your Overall\nExpense Reports");

        
        mChart.animateXY(1500, 1500);
        // mChart.spin(2000, 0, 360);
 
        
/*        Legend l = mChart.getLegend();


        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
  */    

    }

   
    private void setData() {

      
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        
        for (int i = 0; i < categoryList.size(); i++) {
        	HashMap<String, String> map = categoryList.get(i);
    		
    		// adding each child node to HashMap key => value
    String nm=map.get(TAG_category);
   int amt=Integer.parseInt(map.get(TAG_sum));
xVals.add(nm);    		
        	yVals1.add(new Entry(amt, i));
        }

        
     //  for (int i = 0; i < categoryList.size(); i++)
//            xVals.add(mParties[i % mParties.length]);
       
        PieDataSet set1 = new PieDataSet(yVals1, "Expense Results");
        set1.setSliceSpace(3f);
        
        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        
        colors.add(ColorTemplate.getHoloBlue());

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
       }
    

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


	class test extends AsyncTask<String, String, String>
	{
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(PieChartActivity.this);
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
				params.add(new BasicNameValuePair("from",from));
				params.add(new BasicNameValuePair("to",to));
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
	 	String zoneName = c.getString(TAG_category);
	 	String amount = c.getString(TAG_sum);
		// creating new HashMap
		Log.d("All Data: "+i ,zoneName );
		HashMap<String, String> map = new HashMap<String, String>();
		
		// adding each child node to HashMap key => value
		map.put(TAG_category, zoneName);
		map.put(TAG_sum, amount);
		
		// adding HashList to ArrayList
		categoryList.add(map);
		Log.d("All Data: "+i, categoryList.get(i).get(TAG_category));

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
		setData();

		runOnUiThread(new Runnable() {
			public void run() {
	 			/**
				 * Updating parsed JSON data into ListView
				 * */
				/*ListAdapter adapter = new SimpleAdapter(ExpenseList.this, categoryList,
						R.layout.my, new String[] { TAG_expense,
								TAG_amount},new int[] { R.id.tv1,R.id.tv2 });
				// updating listview
						
					setListAdapter(adapter);*/
					}  });

		}


	}

    // private void removeLastEntry() {
    //
    // PieData data = mChart.getDataOriginal();
    //
    // if (data != null) {
    //
    // PieDataSet set = data.getDataSet();
    //
    // if (set != null) {
    //
    // Entry e = set.getEntryForXIndex(set.getEntryCount() - 1);
    //
    // data.removeEntry(e, 0);
    // // or remove by index
    // // mData.removeEntry(xIndex, dataSetIndex);
    //
    // mChart.notifyDataSetChanged();
    // mChart.invalidate();
    // }
    // }
    // }
    //
    // private void addEntry() {
    //
    // PieData data = mChart.getDataOriginal();
    //
    // if (data != null) {
    //
    // PieDataSet set = data.getDataSet();
    // // set.addEntry(...);
    //
    // data.addEntry(new Entry((float) (Math.random() * 25) + 20f,
    // set.getEntryCount()), 0);
    //
    // // let the chart know it's data has changed
    // mChart.notifyDataSetChanged();
    //
    // // redraw the chart
    // mChart.invalidate();
    // }
    // }
}


