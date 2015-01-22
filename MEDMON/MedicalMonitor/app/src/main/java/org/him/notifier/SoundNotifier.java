package org.him.notifier;

/**
 * Created by Saurabh on 1/11/2015.
 */
import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class SoundNotifier {

    public static void SoundNotify(Activity activity)
    {
        try {
            //Get Direct path to default ringtone.
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //Use direct path to ringtone.
            Ringtone r = RingtoneManager.getRingtone(activity.getApplicationContext(), notification);
            //Play ringtone
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
