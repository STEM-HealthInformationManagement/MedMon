package org.him.medicalmonitor;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Timer;


import java.util.TimerTask;

import org.him.filemanager.InputOutput;









import org.him.filemanager.ViperExtension;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

/**THIS USES ACTIVITY_NEXT.XML. IT IS THE SECOND LAYOUT, NOT THE MAIN.*/
@SuppressLint({ "NewApi", "DefaultLocale" })
public class MainActivity extends Activity {
	
	
/*	
	static FileWriter cfgWrite;
	public static FileReader cfgRead;
	public static Scanner cfgScan;
	Logger log = Logger.getLogger(MainActivity.class.getName());
    FileHandler f;
    static FileHandler fs;
	static Logger logs;

	static Context context;*/
	static File cfg;
	int pickedHour;
	int pickedMinute;
	String trailingZero = "";
	Timer timer = new Timer();
	private final int interval = 3000;
	private Handler handler = new Handler();
	static Intent mServiceIntent = new Intent();
	String viper = ".vpr";
	static int currentSeconds;
	static int currentMinutes;
	static int currentHours;
	static int currentDays;
	static String t_currentSeconds;
	static String t_currentMinutes;
	static int t_currentHours;
	static String t_currentDays;
	static int am_pm;
	static String am_pm_text;
	Uri medmonNotification = null;
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		final Button saveButton = (Button) findViewById(R.id.setupButton);
		//TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		saveButton.setEnabled(false);
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(rg.getCheckedRadioButtonId() == -1)
				{
					saveButton.setEnabled(false);
				}
				else
				{
					saveButton.setEnabled(true);
				}
			}
		});
		rg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rg.clearCheck();
				
			}
		});
		

		
		//startService(new Intent(this, ReminderService.class));
		
		//pickedHour = tp.getCurrentHour();
		//pickedMinute = tp.getCurrentMinute();
		
		Calendar calendar = Calendar.getInstance();
		currentMinutes = calendar.get(Calendar.MINUTE);
		currentHours = calendar.get(Calendar.HOUR);
		am_pm = calendar.get(Calendar.AM_PM);
		//t_currentMinutes = String.format("%2d", currentMinutes);
		NumberFormat formatter = new DecimalFormat("##00");
		t_currentMinutes = formatter.format(currentMinutes);
		
		
		/*Runnable runnable = new Runnable(){
		    public void run() {
		    	mServiceIntent.setAction("org.him.medicalmonitor.ReminderService");
				startService(mServiceIntent);
		    }
		};
		//handler.postAtTime(runnable, System.currentTimeMillis()+interval);
		handler.postDelayed(runnable, interval);*/
		
/*		Intent mServiceIntent = new Intent();
		mServiceIntent.setAction("org.him.medicalmonitor.ReminderService");
		  startService(mServiceIntent);*/
		
		//context = this.getApplicationContext();
		/*tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				//String.format("%03d", minute);
				pickedHour = hourOfDay;
				pickedMinute = minute;
				if(minute > -1 && minute < 10)
				{
					trailingZero = "0";
				}
				else
				{
					trailingZero = "";
				}
			}
		});*/

		
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//VibrationManager.Vibrate(MainActivity.this);
				//SoundNotifier.SoundNotify(MainActivity.this);
				String reminder = "";
				
				int timeInMinutes = (currentHours * 60);
				int timeInSeconds = (timeInMinutes * 60) + (currentMinutes * 60);
				int timeInMillis = timeInSeconds * 1000;
				String fullTime = timeInMillis + "";
				InputOutput.Write("ttm" + viper, fullTime);
				if(rg.getCheckedRadioButtonId() == R.id.radioButton1)
				{
					reminder = "1";
					t_currentHours = currentHours + 6;
				}
				else if(rg.getCheckedRadioButtonId() == R.id.radioButton2)
				{
					reminder = "2";
					t_currentHours = currentHours + 5;
				}
				else if(rg.getCheckedRadioButtonId() == R.id.radioButton3)
				{
					reminder = "3";
					t_currentHours = currentHours + 4;
				}
				else if(rg.getCheckedRadioButtonId() == R.id.radioButton4)
				{
					reminder = "4";
					t_currentHours = currentHours + 3;
				}
				else if(rg.getCheckedRadioButtonId() == R.id.radioButton5)
				{
					reminder = "5";
					t_currentHours = currentHours + 2;
					t_currentHours = 1;

				}
				else if(rg.getCheckedRadioButtonId() == R.id.radioButton6)
				{
					reminder = "6";
					//t_currentHours = currentHours + 1;
					//t_currentHours = 1;
					currentMinutes += 30;
				}
				else
				{
					//reminder = "0";
				}
				if(am_pm == 1)
				{
					am_pm_text = "PM";
				}
				else
				{
					am_pm_text = "AM";
				}
				if(currentHours == 0)
				{
					currentHours = 12;
				}
				
				//long hour_min_to_millis = t_currentHours * 3600000 + currentMinutes * 60000;
				long hour_min_to_millis = t_currentHours * 6000;
				InputOutput.Write("med" + viper, currentHours + ":" + t_currentMinutes + " " + am_pm_text);
				InputOutput.Write("medLG" + viper, hour_min_to_millis + "");
				InputOutput.Write("rem" + viper, reminder);
				
				//showNotification();
				
				//Stop Service
				stopReminderSVC();
				//Start Service Again because the file changed
				mServiceIntent.setAction("org.him.medicalmonitor.ReminderService");
				startService(mServiceIntent);
				
				
				Intent i = new Intent(MainActivity.this, NextActivity.class);
	            startActivity(i);	
	            }
		});
		System.out.println(System.getProperty("user.dir"));
		//tv1.setText(System.getProperty("user.dir"));
		//tv1.setText(getApplicationInfo().dataDir);
		//File f = new File(Environment.getRootDirectory(), "");
		tv1.setText(Environment.getRootDirectory().toString());
		
	}
	
	@SuppressWarnings("static-access")
	public void showNotification(){
		 
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        
        medmonNotification.fromFile(new File(""));
        
        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(MainActivity.this, NextActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        
        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)
             
            .setContentTitle("Medicine Alert!")
            .setContentText("Please take your medicine.")
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentIntent(pIntent)
            .setSound(soundUri)
             
            .addAction(R.drawable.ic_launcher, "View", pIntent)
            .addAction(0, "Remind", pIntent)
             
            .build();
        	mNotification.tickerText = "Medical Alert! \n Please take your medicine.";
        	mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 
        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        notificationManager.notify(0, mNotification);
    }
	
	public void onBackPressed()
	{
		return;
	}
	
	public void stopReminderSVC()
	{
		MainActivity.this.stopService(mServiceIntent);
	}
	
}
