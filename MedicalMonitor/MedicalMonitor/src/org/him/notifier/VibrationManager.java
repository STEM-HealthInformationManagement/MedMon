package org.him.notifier;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class VibrationManager {
	
	public static void Vibrate(Activity activity)
	{
		// Get instance of Vibrator from current Context
		Vibrator vb = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

		// Start without a delay
		// Vibrate for 100 milliseconds
		// Sleep for 1000 milliseconds
		long[] pattern = {0, 1000, 3000};

		// The '0' here means to repeat indefinitely
		// '-1' would play the vibration once
		vb.vibrate(pattern, 0);
	}
	
	public static void Vibrate(Activity activity, int startDelay, int lastFor, int sleepFor, int repetition)
	{

		Vibrator vb = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

		long[] pattern = {startDelay, lastFor, sleepFor};

		// The '0' here means to repeat indefinitely
		// '-1' would play the vibration once
		vb.vibrate(pattern, 0);
	}
}
