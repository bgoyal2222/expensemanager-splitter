package com.example.expensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Connet extends Activity {
	
	public static String url = "http://192.168.0.108/";
	//public static String url = "http://192.168.0.20/";
	//public static String url = "http://192.168.43.198/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connet, menu);
		return true;
	}

}

