package com.example.expensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;

public class SetLimit2 extends TabActivity implements TabHost.TabContentFactory {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_set_limit2);
		
		TabHost tabHost = getTabHost();
		  LayoutInflater.from(this).inflate(R.layout.activity_set_limit2,tabHost.getTabContentView(), true);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Overall").setContent(new Intent(this,SetDate.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Category").setContent(new Intent(this,CategoryLimit.class)));
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.set_limit2, menu);
//		return true;
//	}

	@Override
	public View createTabContent(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
