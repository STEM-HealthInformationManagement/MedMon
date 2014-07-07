package org.him.medicalmonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.him.filemanager.InputOutput;
import org.him.notifier.SoundNotifier;
import org.him.notifier.VibrationManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NextActivity extends Activity {

	File cfg;
	static FileWriter cfgWrite;
	public static FileReader cfgRead;
	public static Scanner cfgScan;
	Logger log = Logger.getLogger(MainActivity.class.getName());
    FileHandler f;
    static FileHandler fs;
	static Logger logs;
	TextView counterText;
	TextView getTickerText;
	static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.setupButton);
		counterText = (TextView) findViewById(R.id.counterTextView);
		getTickerText = (TextView) findViewById(R.id.tickerTextView);
		context = this.getApplicationContext();
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				VibrationManager.Vibrate(NextActivity.this);
				SoundNotifier.SoundNotify(NextActivity.this);
				Intent i = new Intent(NextActivity.this, MainActivity.class);
	            startActivity(i);
			}
		});
		
		counterText.setText(InputOutput.Read("med.vpr"));
		getTickerText.setText(ReminderService.getCountingTicker() + "");
	}
	
	public void onBackPressed()
	{
		return;
	}

}
