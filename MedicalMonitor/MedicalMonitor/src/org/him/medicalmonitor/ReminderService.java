package org.him.medicalmonitor;

import android.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


@SuppressLint("NewApi")
public class ReminderService extends Service {
    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
    private int NOTIFICATION = R.string.yes;

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

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
        System.out.println("Service Started!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        System.out.println("Service Started!");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show();
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
        PendingIntent pIntent = PendingIntent.getActivity(ReminderService.this, 0, intent, 0);
         
        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)
             
            .setContentTitle("Alert!")
            .setContentText("Medical Monitor Service Has Been Started!")
            .setSmallIcon(R.drawable.ic_media_play)
            .setContentIntent(pIntent)
            .setSound(soundUri)
             
            .addAction(R.drawable.ic_media_play, "View", pIntent)
            .addAction(0, "Remind", pIntent)
             
            .build();
        	mNotification.tickerText = "Alert! \n Medical Monitor Service Has Been Started!";
        	mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
 
        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
         
        notificationManager.notify(1, mNotification);
    }
}