package kankan.wheel.widget;

import java.util.Calendar;

import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import com.mrwujay.cascade.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * 自定义timepicker
 * @author zr
 *
 */
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
		setDisplayDefaultTime();
	}
	
	/**
	 * 设置当前控件显示当前系统时间
	 * @param hour
	 * @param minute
	 */
	private void setDisplayDefaultTime(){
		Calendar calendar = Calendar.getInstance();
		mViewHour.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY));
		mViewMinute.setCurrentItem(calendar.get(Calendar.MINUTE));
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
	
	/**
	 * 设置timepicker分割线的颜色
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setDividerColor(int r, int g, int b){
		mViewHour.setDividerColor(r, g, b);
		mViewMinute.setDividerColor(r, g, b);
	}
	
	/**
	 * 获取当前timepicker显示的时间
	 * @return
	 */
	public String getCurTime(){
		Calendar calendar =  Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String hour = mHourDatas[mViewHour.getCurrentItem()];
		String mint = mMinuteDatas[mViewMinute.getCurrentItem()];
		StringBuilder sb =new StringBuilder();
		return sb.append(year).append(String.format("%02d",month)).append(String.format("%02d",day)).append("T").append(hour).append(":").append(mint).append(":00").toString();
	}
	
	/**
	 * 设置timepicker显示时间
	 * @param time
	 */
	public void setCurTime(String time){
		try{
			Calendar calendar = convertToCalendar(time);
			if(calendar !=null){
				mViewHour.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY));
				mViewMinute.setCurrentItem(calendar.get(Calendar.MINUTE));
			}
			else{
				Log.e("timepicker", "setCurTime error");
			}
		}
		catch(Exception e){
			Log.e("timepicker", "setCurTime error");
		}
	}
	
	
	/**
	 * 将字符串时间转换为日历时间
	 * @param time
	 * @return
	 */
	private Calendar convertToCalendar(String time){
		Calendar calendar = null;
		try{
			//20140614T17:35:23
			calendar = Calendar.getInstance();
			int year = Integer.parseInt(time.substring(0, 4));
			int month =  Integer.parseInt(time.substring(4,6));
			int day = Integer.parseInt(time.substring(6,8));
			int hour = Integer.parseInt(time.substring(9,11));
			int minute = Integer.parseInt(time.substring(12,14));
			
			int second =Integer.parseInt( time.substring(15,17));
			calendar.set(year, month, day, hour, minute, second);
		}catch(Exception e){
			e.printStackTrace();
		}			
		return calendar;
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
