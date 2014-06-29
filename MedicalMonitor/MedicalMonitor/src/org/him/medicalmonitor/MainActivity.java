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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
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
				cfg = new File("/");
				cfg.setExecutable(true);
				cfg.setWritable(true);
				InputOutput.Write(pickedHour + ":" + pickedMinute, "med.vpr");
				Intent i = new Intent(MainActivity.this, NextActivity.class);
	            startActivity(i);
			}
		});
		System.out.println(System.getProperty("user.dir"));
		//tv1.setText(System.getProperty("user.dir"));
		tv1.setText(getApplicationInfo().dataDir);
		//File f = new File(Environment.getRootDirectory(), "");
		//tv1.setText(Environment.getRootDirectory().toString());
		
	}
	
	/**
	 * Writes valid string into a specified file in the same directory
	 * the application resources are contained in.
	 * 
	 * @param s : String - String to write into file.
	 * @param file : String - File name and path succeeding resource directory.
	 
	public static void Write(String s, String file)
	{
		try 
		{
			cfg = new File(context.getFilesDir() + "/" + file);
			cfg.createNewFile();
			cfgWrite = new FileWriter(file);
			cfgWrite.write(s);
			cfgWrite.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			
			/**Play a sound in the following line.
			//playWindowsError();
			
			
			
		    try 
		    {

		      //Log all OTHER errors 
		      fs = new FileHandler("err/oerdump.vpr", true);
		      logs.setLevel(Level.ALL);		      
		      logs.addHandler(fs);
		      
		      
		      SimpleFormatter sf = new SimpleFormatter();
		      fs.setFormatter(sf);

		      //Log a string when other error occurs 
			    logs.log(Level.SEVERE, "UNABLE TO READ PREFERENCE FILES");

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
	
	public void Read()
	{
		try 
		{
			//READ a1 Checkbox
			cfgRead = new FileReader("wh/rdt.vpr");
			cfgScan = new Scanner(cfgRead);
			String pref = cfgScan.next();
			
			//READ a2 Checkbox
			/*cfgRead = new FileReader("wh/rdt2.vpr");
			cfgScan = new Scanner(cfgRead);
			String pref2 = cfgScan.next();*/
			
			
			//READ e1 Checkbox
			/*PokerFrame.cfgRead = new FileReader("wh/rdt1.vpr");
			PokerFrame.cfgScan = new Scanner(PokerFrame.cfgRead);
			String pref1 = PokerFrame.cfgScan.next();
			
			//ALL GENERAL READS HERE
			if(pref.contains("a1=1"))
			{
				//a1.setSelected(true);
				
			}
			else
			{
				//a1.setSelected(false);
			}
			
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
			}
			
			cfgScan.close();
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
		
		
	}*/
}
