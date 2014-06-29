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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button b = (Button) findViewById(R.id.setupButton);
		counterText = (TextView) findViewById(R.id.counterTextView);
		context = this.getApplicationContext();
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*try {
		    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
		    r.play();
		} catch (Exception e) {
		    e.printStackTrace();
		}
				// Get instance of Vibrator from current Context
				Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

				// Start without a delay
				// Vibrate for 100 milliseconds
				// Sleep for 1000 milliseconds
				long[] pattern = {0, 1000, 3000};

				// The '0' here means to repeat indefinitely
				// '-1' would play the vibration once
				vb.vibrate(pattern, 0);*/

				Intent i = new Intent(NextActivity.this, MainActivity.class);
	            startActivity(i);
			}
		});
		
		Read();
	}
	
	public void Read()
	{
		try 
		{
			//READ a1 Checkbox
			//cfgRead = new FileReader(context.getFilesDir() + "/" + "med.vpr");
			//cfgScan = new Scanner(cfgRead);
			//String pref = cfgScan.next();
			
			 FileInputStream fis = openFileInput("med.vpr");
			   InputStreamReader isr = new InputStreamReader(fis);
			   BufferedReader bufferedReader = new BufferedReader(isr);
			   StringBuilder sb = new StringBuilder();
			   String line;
			   while ((line = bufferedReader.readLine()) != null) {
			       sb.append(line);
			       counterText.setText(sb);
			   }
			
			//READ a2 Checkbox
			/*cfgRead = new FileReader("wh/rdt2.vpr");
			cfgScan = new Scanner(cfgRead);
			String pref2 = cfgScan.next();*/
			
			
			//READ e1 Checkbox
			/*PokerFrame.cfgRead = new FileReader("wh/rdt1.vpr");
			PokerFrame.cfgScan = new Scanner(PokerFrame.cfgRead);
			String pref1 = PokerFrame.cfgScan.next();
			*/
			//ALL GENERAL READS HERE
			//counterText.setText(pref);
			
			//ALL SETTINGS READS HERE
			
			
			
			//ALL CONNECTION READS HERE
			
			
			
			//ALL OTHER READS HERE
			/*if(pref1.contains("e1=1"))
			{
				e1.setSelected(true);
			}
			else
			{
				e1.setSelected(false);
			}*/
			
			//cfgScan.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			
			
			
		    try 
		    {

		      //Log all OTHER errors 
		      f = new FileHandler("err/oerdump.vpr", true);
		      log.setLevel(Level.ALL);		      
		      log.addHandler(f);
		      
		      
		      SimpleFormatter sf = new SimpleFormatter();
		      f.setFormatter(sf);

		      //Log a string when connection error occurs 
			    log.log(Level.SEVERE, "UNABLE TO READ PREFERENCE FILES");


		    }
		    catch (SecurityException e1) 
		    {
		      e1.printStackTrace();
		    }
		    catch (IOException e2) 
		    {
		      e2.printStackTrace();
		    }
		}
		
		
	}
}
