package org.him.medicalmonitor;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.him.filemanager.InputOutput;
import org.him.notifier.SoundNotifier;
import org.him.notifier.VibrationManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

/**THIS USES ACTIVITY_NEXT.XML. IT IS THE SECOND LAYOUT, NOT THE MAIN.*/
@SuppressLint("NewApi")
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
		Button saveButton = (Button) findViewById(R.id.setupButton);
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		pickedHour = tp.getCurrentHour();
		pickedMinute = tp.getCurrentMinute();
		//context = this.getApplicationContext();
		tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				pickedHour = hourOfDay;
				pickedMinute = minute;
			}
		});
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//VibrationManager.Vibrate(MainActivity.this);
				//SoundNotifier.SoundNotify(MainActivity.this);
				String day = "";
				if(pickedHour >= 12 && pickedHour < 24)
				{
					day = "PM";
					pickedHour -= 12;
				}
				else if (pickedHour >= 0 && pickedHour < 12 )
				{
					day = "AM";
					if(pickedHour == 0)
					{
						pickedHour += 12;
					}
				}
				InputOutput.Write("med.vpr", pickedHour + ":" + pickedMinute + " " + day);

				showNotification();
				
				Intent i = new Intent(MainActivity.this, NextActivity.class);
	            startActivity(i);			}
		});
		System.out.println(System.getProperty("user.dir"));
		//tv1.setText(System.getProperty("user.dir"));
		//tv1.setText(getApplicationInfo().dataDir);
		//File f = new File(Environment.getRootDirectory(), "");
		tv1.setText(Environment.getRootDirectory().toString());
		
	}
	
	public void showNotification(){
		 
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         
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
	
}
