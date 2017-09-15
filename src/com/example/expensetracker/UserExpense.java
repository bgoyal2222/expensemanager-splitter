package com.example.expensetracker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.expensetracker.SetDate.test;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class UserExpense extends Activity implements OnClickListener{

	TextView tv1,tv2;
	boolean d1=false,d2=false;
	Button b1,b2,b3;
	
	final Calendar dateAndTime=Calendar.getInstance();
	DateFormat fmtDateAndTime=DateFormat.getDateInstance();
	DatePickerDialog.OnDateSetListener d;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_expense);
		
		b1=(Button)findViewById(R.id.btnf);
		b2=(Button)findViewById(R.id.btnt);
		b3=(Button)findViewById(R.id.get);
		tv1=(TextView)findViewById(R.id.fdate);
		tv2=(TextView)findViewById(R.id.tdate);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		
		d=new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				dateAndTime.set(Calendar.YEAR, arg1);
				dateAndTime.set(Calendar.MONTH,arg2);
				dateAndTime.set(Calendar.DAY_OF_MONTH, arg3);
				updateLabel();
			}
		};
		
	}

	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId()==R.id.btnf)
		{
			d1=true;
			d2=false;
	//DatePickerDialog d1=new DatePickerDialog(DateDialogSampleActivity.this,	d,dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
	DatePickerDialog d1=new DatePickerDialog(UserExpense.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
	d1.show();
		}
		else if(arg0.getId()==R.id.btnt)
		{
			d2=true;
			d1=false;
			DatePickerDialog d1=new DatePickerDialog(UserExpense.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
			d1.show();
		}
		else if(arg0.getId()==R.id.get)
		{
			Intent it=new Intent(getApplicationContext(),ExpenseList.class);
			it.putExtra("from",tv1.getText().toString());
			it.putExtra("to",tv2.getText().toString());
			startActivity(it);
		}
		
	}
	
	private void updateLabel() {
		if(d1==true){
			Date from=new Date();
			from=dateAndTime.getTime();
			String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
			//tv1.setText(fmtDateAndTime.format(dateAndTime.getTime()));
			tv1.setText(format);
		}
		else if(d2==true){
			Date from=new Date();
			from=dateAndTime.getTime();
			String format=new SimpleDateFormat("yyyy-MM-dd").format(from);
			//tv2.setText(fmtDateAndTime.format(dateAndTime.getTime()));
			tv2.setText(format);
		}
		}

}
