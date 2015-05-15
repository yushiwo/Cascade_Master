package com.mrwujay.cascade.activity;

import com.mrwujay.cascade.R;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.TimePicker;
import kankan.wheel.widget.TimePicker.TimePickerListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnWheelChangedListener {
	private WheelView mViewHour;
	private WheelView mViewMinute;
	private Button mBtnConfirm;
	private TextView mTextViewTime;
	private TimePicker mTimePicker;
	
	private String[] mHourDatas = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	private String[] mMinuteDatas = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
			, "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"
			, "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55"
			, "56", "57", "58", "59"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpViews();
		setUpListener();
		setUpData();
	}
	
	/**
	 * 初始化控件
	 */
	private void setUpViews() {
		mViewHour = (WheelView) findViewById(R.id.id_hour);
		mViewMinute = (WheelView) findViewById(R.id.id_minute);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
		mTextViewTime = (TextView)findViewById(R.id.id_time);
		mTimePicker = (TimePicker)findViewById(R.id.id_timepicker);
	}
	
	private void setUpListener() {
    	// 设置省监听
		mViewHour.addChangingListener(this);
    	// 设置城市监听
		mViewMinute.addChangingListener(this);
    	// 设置“确定”按钮监听事件
    	mBtnConfirm.setOnClickListener(this);
    	
    	//设置TimePicker组合控件监听
    	mTimePicker.setTimePickerListener(new TimePickerListener() {
			
			@Override
			public void onPick(String hour, String minute) {
				mTextViewTime.setText("时间:" + hour + ":" + minute);
			}
		});
    }
	
	private void setUpData() {
		mViewHour.setViewAdapter(new ArrayWheelAdapter<String>(MainActivity.this, mHourDatas));
		mViewMinute.setViewAdapter(new ArrayWheelAdapter<String>(MainActivity.this, mMinuteDatas));
		// 设置每个滚轮显示子item的数量
		mViewHour.setVisibleItems(7);
		mViewMinute.setVisibleItems(7);
		
		mTimePicker.setCurrentTime(10, 29);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewHour) {
			showSelectedResult();
		} else if (wheel == mViewMinute) {
			showSelectedResult();
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			showSelectedResult();
			break;
		default:
			break;
		}
	}

	private void showSelectedResult() {
		mTextViewTime.setText("时间:" + mHourDatas[mViewHour.getCurrentItem()] + ":" + mMinuteDatas[mViewMinute.getCurrentItem()]);
	}
}
