package org.him.notifier;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class SoundNotifier {

	public static void SoundNotify(Activity activity)
	{
		try {
		    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    Ringtone r = RingtoneManager.getRingtone(activity.getApplicationContext(), notification);
		    r.play();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
}
