package kankan.wheel.widget;

import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import com.mrwujay.cascade.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class TimePicker extends LinearLayout implements OnWheelChangedListener{

	private static final int MSG_TIME_PICKED = 0;
	
	/** 小时选择器 */
	private WheelView mViewHour;
	/** 分钟选择器 */
	private WheelView mViewMinute;
	/** timepicker监听接口 */
	private TimePickerListener mTimePickerListener;
	
	private String[] mHourDatas = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	private String[] mMinuteDatas = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
			, "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"
			, "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55"
			, "56", "57", "58", "59"};
	
	 private Handler mHandler = new Handler() {
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case MSG_TIME_PICKED:
	                if (mTimePickerListener != null)
	                    mTimePickerListener.onPick(mHourDatas[mViewHour.getCurrentItem()], mMinuteDatas[mViewMinute.getCurrentItem()]);
	                break;
	            }
	        };
	    };
	
	@SuppressLint("NewApi") 
	public TimePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public TimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TimePicker(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context){
		LayoutInflater.from(context).inflate(R.layout.colorui_timepicker, this, true);  
		setUpViews();
		setUpData(context);
		setUpListener();
		
		setTextSize(25);
		setTextColor(0xFF700070);
	}
	
	/**
	 * 设置当前控件显示时间 
	 * @param hour
	 * @param minute
	 */
	public void setCurrentTime(int hour, int minute){
		mViewHour.setCurrentItem(hour);
		mViewMinute.setCurrentItem(minute);
	}
	
	/**
	 * 初始化控件
	 */
	private void setUpViews() {
		mViewHour = (WheelView) findViewById(R.id.id_hour);
		mViewMinute = (WheelView) findViewById(R.id.id_minute);
	}
	
	/**
	 * 设置控件显示数据
	 * @param context
	 */
	private void setUpData(Context context) {
		mViewHour.setViewAdapter(new ArrayWheelAdapter<String>(context, mHourDatas));
		mViewMinute.setViewAdapter(new ArrayWheelAdapter<String>(context, mMinuteDatas));
		// 设置每个滚轮显示子item的数量
		mViewHour.setVisibleItems(7);
		mViewMinute.setVisibleItems(7);
	}
	
	/**
	 * 设置监听
	 */
	private void setUpListener() {
    	// 设置小时监听
		mViewHour.addChangingListener(this);
    	// 设置分钟监听
		mViewMinute.addChangingListener(this);
    }
	
	/**
	 * 设置文字大小
	 */
	public void setTextSize(int textSize){
		mViewHour.getViewAdapter().setTextSize(textSize);
		mViewMinute.getViewAdapter().setTextSize(textSize);
	}
	
	/**
	 * 设置文字颜色
	 */
	public void setTextColor(int textColor){
		mViewHour.getViewAdapter().setTextColor(textColor);
		mViewMinute.getViewAdapter().setTextColor(textColor);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewHour) {
			mHandler.removeMessages(MSG_TIME_PICKED);
            mHandler.sendEmptyMessageDelayed(MSG_TIME_PICKED, 200);
		} else if (wheel == mViewMinute) {
			mHandler.removeMessages(MSG_TIME_PICKED);
            mHandler.sendEmptyMessageDelayed(MSG_TIME_PICKED, 200);
		}
	}
	
	
	 public void setTimePickerListener(TimePickerListener l) {
		 this.mTimePickerListener = l;
	 }

    public interface TimePickerListener {
    	void onPick(String hour, String minute);
    }
	
}
