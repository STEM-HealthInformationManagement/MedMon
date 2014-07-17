package org.him.medicalmonitor;

import java.util.Timer;

import org.him.filemanager.InputOutput;

import android.R;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


@SuppressLint("NewApi")
public class ReminderService extends Service {
    private NotificationManager mNM;
    
	Timer timer = new Timer();
	long countEvery;
	CountDownTimer ct;

	private final int interval = 3000;
	String viper = ".vpr";
	private Handler handler = new Handler();

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.yes;
    public static long getTicks;
    
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public ReminderService getService() {
            return ReminderService.this;
        }
    }

    @Override
    public void onCreate() {
    	
    	System.out.println("Starting Service...");
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        
/*        Runnable runnable = new Runnable(){
		    public void run() {
		        // Display a notification about us starting.  We put an icon in the status bar.
		        showNotification();
		    }
		};
		//handler.postAtTime(runnable, System.currentTimeMillis()+interval);
		handler.postDelayed(runnable, interval);*/
        System.out.println("Service Started!");
        
        String checkReminder = InputOutput.Read("rem" + viper);
        countEvery = InputOutput.ReadLong("medLG" + viper); 
        
        final int getPrescriptionTiming = InputOutput.ReadInteger("rem" + viper);
        System.out.println(getPrescriptionTiming);
        
        int seconds, minutes, hours, days;
        int milliseconds = 0;
        
        seconds = milliseconds/1000;
        days = seconds / 86400;
        hours = (seconds/3600) - (days*24);
        minutes = (seconds/60) - (days*1440) - (hours*60);
        seconds = seconds - (days*86400) - (hours*3600) - (minutes*60);	
        
        //AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        
        
        
        //long time = c.getLong(c.getColumnIndex(Notification.COL_DATETIME));
        
        
/*        if(checkReminder.equals("1"))
        {
        	countEvery = 6 * 21600; 
        }
        else if(checkReminder.equals("2"))
        {
        	countEvery = 5 * 18000;
        }
        else if(checkReminder.equals("3"))
        {
        	countEvery = 4 * 14400;
        }
        else if(checkReminder.equals("4"))
        {
        	countEvery = 3 * 10800;
        }
        else if(checkReminder.equals("5"))
        {
        	countEvery = 2 * 7200;
        }
        else if(checkReminder.equals("6"))
        {
        	countEvery = 10000;
        }
        else
        {
        	countEvery = 0;
        }*/
        ct = new CountDownTimer(countEvery, 1000) {

            public void onTick(long millisUntilFinished) {
            	//Do Nothing Yet
            	getTicks = millisUntilFinished; 
            	getTicks--;
            }

            public void onFinish() {
            	for(int i = 0; i <= getPrescriptionTiming; i++)
            	{
            		System.out.println(i);
            		
            		if(i == getPrescriptionTiming)
            		{
            			System.out.println("exiting loop...");
            			break;
            		}
            		else
            		{
            			//ct.start();
            			showMedicalNotification();
            		}
            		try {
						Thread.sleep(countEvery);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            }
         }.start();
        
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        System.out.println("Service Started!");
        
        //Return STICKY to keep running this service until forcefully stopped.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        //Toast.makeText(this, "Medicine Reminder Saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    /**
     * Show a notification while this service is running.
     */
 /*   @SuppressWarnings("deprecation")
	private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.yes);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_menu_more, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.copy),
                       text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);
    }*/
    
	public void showNotification(){
		 
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         
        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(ReminderService.this, NextActivity.class);
        Intent intent2 = new Intent(ReminderService.this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(ReminderService.this, 0, intent, 0);
        PendingIntent pIntent2 = PendingIntent.getActivity(ReminderService.this, 0, intent2, 0);
        
         
        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)
             
            .setContentTitle("Alert!")
            .setContentText("Medical Monitor Service Has Been Started!")
            .setSmallIcon(R.drawable.ic_media_play)
            .setContentIntent(pIntent)
            .setSound(soundUri)
             
            .addAction(R.drawable.ic_media_play, "View", pIntent)
            .addAction(R.drawable.ic_menu_close_clear_cancel, "Stop Reminding", pIntent2)
             
            .build();
        	mNotification.tickerText = "Alert! \n"
        			+ " Medical Monitor Service Has Been Started!";
        	mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 
        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        notificationManager.notify(1, mNotification);
    }
	
	public void showMedicalNotification(){
		 
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         
        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(ReminderService.this, NextActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(ReminderService.this, 0, intent, 0);
        
         
        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)
             
            .setContentTitle("Excuse Us!")
            .setContentText("Please Take Your Medicine")
            .setSmallIcon(R.drawable.zoom_plate)
            .setContentIntent(pIntent)
            .setSound(soundUri)
             
            .build();
        	mNotification.tickerText = "Excuse Us! \n"
        			+ " Please Take Your Medicine!";
        	mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 
        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        notificationManager.notify(1, mNotification);
    }
	
	public static long getCountingTicker()
	{
		return getTicks;
	}
}