package com.mrwujay.cascade.activity;

import com.mrwujay.cascade.R;

import kankan.wheel.widget.TimePicker;
import kankan.wheel.widget.TimePicker.TimePickerListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private Button mBtnConfirm;
	private Button mBtnSet;
	private TextView mTextViewTime;
	private TimePicker mTimePicker;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViews();
		setUpListener();
	}
	
	/**
	 * 初始化控件
	 */
	private void setUpViews() {
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		mBtnSet = (Button) findViewById(R.id.btn_settime);
		mTextViewTime = (TextView)findViewById(R.id.id_time);
		mTimePicker = (TimePicker)findViewById(R.id.id_timepicker);
		
//		mTimePicker.setCurTime("00000000T12:45:02");
		mTimePicker.setTextSize(25);
		mTimePicker.setTextColor(0xFF700070);
//		mTimePicker.setDividerColor(50, 143, 50);
		mTimePicker.setDividerColor(0xFF700070);
		mTimePicker.setBgColor(0xFF909090);
		mTimePicker.setDividerHeight(3);
	}
	
	private void setUpListener() {
    	// 设置“确定”按钮监听事件
    	mBtnConfirm.setOnClickListener(this);
    	mBtnSet.setOnClickListener(this);
    	
    	//设置TimePicker组合控件监听
    	mTimePicker.setTimePickerListener(new TimePickerListener() {
			
			@Override
			public void onPick(String hour, String minute) {
				mTextViewTime.setText("时间:" + hour + ":" + minute);
			}
		});
    }
	


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			showSelectedResult();
			break;
		case R.id.btn_settime:
			mTimePicker.setCurTime("00000000T12:45:02");
			break;
		default:
			break;
		}
	}

	private void showSelectedResult() {
		mTextViewTime.setText("时间:" + mTimePicker.getCurTime());
	}
}
