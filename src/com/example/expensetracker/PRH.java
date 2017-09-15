package com.example.expensetracker;



import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

public class PRH extends TabActivity implements TabHost.TabContentFactory,android.view.View.OnClickListener {

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
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
		TabHost tabHost = getTabHost();
		  LayoutInflater.from(this).inflate(R.layout.activity_prh,tabHost.getTabContentView(), true);
		  tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Add Expense").setContent(new Intent(this,   AddGroupExpense.class)));	
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Payable").setContent(new Intent(this,   MyGroupExpenses.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Receivable").setContent(new Intent(this, Receivable.class)));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("History").setContent(new Intent(this, History.class)));
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.prh, menu);
//		return true;
//	}
//
	@Override
	public View createTabContent(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent it=new Intent(getApplicationContext(),Add_participant.class);
		startActivity(it);
		
	}

}
