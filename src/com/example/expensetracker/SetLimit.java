package com.example.expensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SetLimit extends Activity implements OnClickListener{

	Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_limit);
		
		b1=(Button)findViewById(R.id.got);
		b2=(Button)findViewById(R.id.button2);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.set_limit, menu);
//		return true;
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.got)
		{
			Intent it=new Intent(getApplicationContext(),SetDate.class);
			startActivity(it);
		}
		else if(v.getId()==R.id.button2)
		{
			Intent it=new Intent(getApplicationContext(),CategoryLimit.class);
			startActivity(it);
		}
	}

}
